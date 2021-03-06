package com.example.fit4all;

import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ImageFormat;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PointF;
import android.graphics.SurfaceTexture;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.Image;
import android.media.ImageReader;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.util.LruCache;
import android.util.Size;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import ai.fritz.core.Fritz;
import ai.fritz.vision.FritzVision;
import ai.fritz.vision.FritzVisionImage;
import ai.fritz.vision.FritzVisionModels;
import ai.fritz.vision.FritzVisionOrientation;
import ai.fritz.vision.ImageOrientation;
import ai.fritz.vision.ModelVariant;
import ai.fritz.vision.poseestimation.FritzVisionPosePredictor;
import ai.fritz.vision.poseestimation.FritzVisionPoseResult;
import ai.fritz.vision.poseestimation.Keypoint;
import ai.fritz.vision.poseestimation.Pose;
import ai.fritz.vision.poseestimation.PoseOnDeviceModel;


public class fragEjCamera  extends Fragment  implements SurfaceHolder.Callback{
    MainActivity main;
    TextView txtTiempo,txtNombre;
    ImageView imgE;
    ArrayList<Ejercicio> lisEj;
    private FritzVisionPosePredictor posePredictor;
    private FritzVisionPoseResult poseResult;
    private Activity act;
    Context con;
    ImageView imageView;
    int acumM, acumB;
    CountDownTimer countDown;
    Boolean start;
    long Start,leftTime;

    ImageView imageViewSuper;

    // A sensitivity parameters we'll use later.
    private float minPoseThreshold = 0.6f;

    List<Pose> arrayPose;

    public Bitmap posesOnImage;
    ImageOrientation imageRotation;

    File pictureFile;
    Bitmap bit;

    private static final String TAG = "AndroidCameraApi";
    private Button takePictureButton;
    private TextureView textureView;
    private static final SparseIntArray ORIENTATIONS = new SparseIntArray();
    static {
        ORIENTATIONS.append(Surface.ROTATION_0, 90);
        ORIENTATIONS.append(Surface.ROTATION_90, 0);
        ORIENTATIONS.append(Surface.ROTATION_180, 270);
        ORIENTATIONS.append(Surface.ROTATION_270, 180);
    }
    private String cameraId;
    protected CameraDevice cameraDevice;
    protected CameraCaptureSession cameraCaptureSessions;
    protected CaptureRequest captureRequest;
    protected CaptureRequest.Builder captureRequestBuilder;
    private Size imageDimension;
    private ImageReader imageReader;
    private File file;
    private static final int REQUEST_CAMERA_PERMISSION = 200;
    private boolean mFlashSupported;
    private Handler mBackgroundHandler;
    private HandlerThread mBackgroundThread;
    private Bitmap bitmap;

    private LruCache<String, Bitmap> mMemoryCache;


    final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 10);

    // Use 1/8th of the available memory for this memory cache.
    final int cacheSize = maxMemory / 8;

    private static MainActivity instance;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vista;
        vista=inflater.inflate(R.layout.layout_ejcamara,container,false);
        main= (MainActivity) getActivity();
        txtTiempo=vista.findViewById(R.id.cronometroEjCam);
        txtNombre=vista.findViewById(R.id.txtNombreEjCam);
        imgE=vista.findViewById(R.id.imagenEjCam);
        acumB=0;
        acumM=0;
        lisEj=main.devolverArrayEj();//LISTASIGUIENTEEJERCICIOOOOOOOOO
        cargarDatos();
        mostrarTiempo();
        comenzar();
        start=true;
        con = getActivity().getApplication();
        Fritz.configure(getContext(), "4f1d35d761a24d328e07e0014c1cd515");
        imageView = vista.findViewById(R.id.txtImg);
        // For accurate
        PoseOnDeviceModel onDeviceModel = FritzVisionModels.getHumanPoseEstimationOnDeviceModel(ModelVariant.ACCURATE);
        posePredictor = FritzVision.PoseEstimation.getPredictor(onDeviceModel);
        textureView = (TextureView) vista.findViewById(R.id.texture);
        assert textureView != null;
        textureView.setSurfaceTextureListener(textureListener);
        takePictureButton = (Button) vista.findViewById(R.id.btn_takepicture);
        assert takePictureButton != null;
        takePictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePicture();
            }
        });

        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                // The cache size will be measured in kilobytes rather than
                // number of items.
                return bitmap.getByteCount() / 1024;
            }
        };

        instance = (MainActivity) MainActivity.getContextOfApplication();

        SurfaceView view = new SurfaceView(con);
        view.getHolder().addCallback(this);


        SurfaceView sfvTrack = (SurfaceView)vista.findViewById(R.id.imagesurface);
        sfvTrack.setZOrderOnTop(true);    // necessary
        SurfaceHolder sfhTrackHolder = sfvTrack.getHolder();
        sfhTrackHolder.setFormat(PixelFormat.TRANSPARENT);

        sfvTrack.setVisibility(View.GONE);
        view.setVisibility(View.GONE);
        takePictureButton.setVisibility(View.GONE);



        act = getActivity();
        return vista;
    }



    public File getOutputMediaFile() {

        File mediaStorageDir = new File(Environment.getExternalStorageDirectory()
                + "/Android/data/"
                + con.getApplicationContext().getPackageName()
                + "/Files");


        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
        String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmm").format(new Date());
        File mediaFile;
        String mImageName = "MI_" + timeStamp + ".jpg";
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + mImageName);
        return mediaFile;
    }

    TextureView.SurfaceTextureListener textureListener = new TextureView.SurfaceTextureListener() {
        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
            //open your camera here
            openCamera();
        }
        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
            // Transform you image captured size according to the surface width and height
            takePicture();
        }
        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
            return false;
        }
        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture surface) {
            takePicture();
        }
    };
    public final CameraDevice.StateCallback stateCallback = new CameraDevice.StateCallback() {
        @Override
        public void onOpened(CameraDevice camera) {
            //This is called when the camera is open
            Log.e(TAG, "onOpened");
            cameraDevice = camera;
            createCameraPreview();
        }
        @Override
        public void onDisconnected(CameraDevice camera) {
            cameraDevice.close();
        }
        @Override
        public void onError(CameraDevice camera, int error) {
            cameraDevice.close();
            cameraDevice = null;
        }
    };
    final CameraCaptureSession.CaptureCallback captureCallbackListener = new CameraCaptureSession.CaptureCallback() {
        @Override
        public void onCaptureCompleted(CameraCaptureSession session, CaptureRequest request, TotalCaptureResult result) {
            super.onCaptureCompleted(session, request, result);
            //Toast.makeText(con, "Saved:" + file, Toast.LENGTH_SHORT).show();
            createCameraPreview();
        }
    };
    public void startBackgroundThread() {
        mBackgroundThread = new HandlerThread("Camera Background");
        mBackgroundThread.start();
        mBackgroundHandler = new Handler(mBackgroundThread.getLooper());
    }
    public void stopBackgroundThread() {
        mBackgroundThread.quitSafely();
        try {
            mBackgroundThread.join();
            mBackgroundThread = null;
            mBackgroundHandler = null;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void takePicture() {
        if(null == cameraDevice) {
            Log.e(TAG, "cameraDevice is null");
            return;
        }
        CameraManager manager = (CameraManager) con.getSystemService(Context.CAMERA_SERVICE);
        try {
            CameraCharacteristics characteristics = manager.getCameraCharacteristics(cameraDevice.getId());
            Size[] jpegSizes = null;
            if (characteristics != null) {
                jpegSizes = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP).getOutputSizes(ImageFormat.JPEG);
            }
            int width = 640;
            int height = 480;
            if (jpegSizes != null && 0 < jpegSizes.length) {
                width = jpegSizes[0].getWidth();
                height = jpegSizes[0].getHeight();
            }
            ImageReader reader = ImageReader.newInstance(width, height, ImageFormat.JPEG, 1);
            List<Surface> outputSurfaces = new ArrayList<Surface>(2);
            outputSurfaces.add(reader.getSurface());
            outputSurfaces.add(new Surface(textureView.getSurfaceTexture()));
            final CaptureRequest.Builder captureBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE);
            captureBuilder.addTarget(reader.getSurface());
            captureBuilder.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO);
            // Orientation
            int rotation = getActivity().getWindowManager().getDefaultDisplay().getRotation();
            captureBuilder.set(CaptureRequest.JPEG_ORIENTATION, ORIENTATIONS.get(rotation));
            //  final File file = new File(Environment.getExternalStorageDirectory()+"/pic.jpg");
            ImageReader.OnImageAvailableListener readerListener = new ImageReader.OnImageAvailableListener() {
                @Override
                public void onImageAvailable(ImageReader reader) {
                    Image image = null;
                    CameraManager manager = (CameraManager) con.getSystemService(Context.CAMERA_SERVICE);
                    try {
                        image = reader.acquireLatestImage();

                        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.personaparada);

                        String cameraId = manager.getCameraIdList()[1];
                        ImageOrientation imageRotationFromCamera = FritzVisionOrientation.getImageOrientationFromCamera(act, cameraId);
                        FritzVisionImage visionImage = FritzVisionImage.fromMediaImage(image,imageRotationFromCamera);
                        poseResult = posePredictor.predict(visionImage);
                        arrayPose = poseResult.getPoses();
                        Log.d("resultadoArray", String.valueOf(poseResult));
                        posesOnImage = visionImage.overlaySkeletons(arrayPose);
                        bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);
                        if(arrayPose.size()>0) {
                            if (start == true) {
                                Pose pose = arrayPose.get(0);
                                Keypoint[] keypoints = pose.getKeypoints();
                                if (lisEj.get(main.iListaEj).getIdEjercicio()== "25") {
                                    Float val = keypoints[13].getPosition().y - keypoints[11].getPosition().y;
                                    Log.d("VALOR", String.valueOf(val));
                                    if (val <= 60) {
                                        Float valor = keypoints[15].getPosition().x - keypoints[16].getPosition().x;
                                        if (valor < 40.00 && valor > 0) {
                                            Log.d("RTA", "abrir");
                                            //Toast.makeText(MainActivity.this, "Tenes q abrir las piernas:", Toast.LENGTH_SHORT).show();
                                            //Log.d("Pose2", "Tenes q abrir las piernassssssssssssssssssssssssssssssssssssssssssssssssssssss")
                                            acumM++;
                                        } else if (keypoints[15].getPosition().x > keypoints[5].getPosition().x && keypoints[16].getPosition().x > keypoints[6].getPosition().x) {
                                            //Log.d("Pose", "Tenes q cerrar las piernas");
                                            Log.d("RTA", "cerrar");
                                            acumM++;
                                            //Toast.makeText(MainActivity.this, "enes q cerrar las piernas", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Log.d("RTA", "bien!");
                                            acumB++;
                                        }
                                    }
                                }
                            }
                        }





                        //dibujarPose(posesOnImage);

                        // imageView.setImageBitmap(posesOnImage);
                        //   textureView.setVisibility(View.GONE);
                        //   takePictureButton.setVisibility(View.GONE);


                        //  ByteBuffer buffer = image.getPlanes()[0].getBuffer();
                        // byte[] bytes = new byte[buffer.capacity()];
                        // buffer.get(bytes);
                        //save(bytes);



                        //} catch (FileNotFoundException e) {
                        // e.printStackTrace();
                        // } catch (IOException e) {
                        // e.printStackTrace();
                    } catch (CameraAccessException e) {
                        e.printStackTrace();
                    } finally {
                        if (image != null) {
                            image.close();
                            // dibujarPose(posesOnImage);

                        }
                    }

                }



                public void save(byte[] bytes) throws IOException {
                    OutputStream output = null;
                    try {
                        output = new FileOutputStream(file);
                        output.write(bytes);
                    } finally {
                        if (null != output) {
                            output.close();
                        }
                    }
                }
            };

            reader.setOnImageAvailableListener(readerListener, mBackgroundHandler);

            final CameraCaptureSession.CaptureCallback captureListener = new CameraCaptureSession.CaptureCallback() {
                @Override
                public void onCaptureCompleted(CameraCaptureSession session, CaptureRequest request, TotalCaptureResult result) {
                    super.onCaptureCompleted(session, request, result);
                    //Toast.makeText(con, "Saved:" + file, Toast.LENGTH_SHORT).show();
                    createCameraPreview();
                }
            };
            cameraDevice.createCaptureSession(outputSurfaces, new CameraCaptureSession.StateCallback() {
                @Override
                public void onConfigured(CameraCaptureSession session) {
                    try {
                        session.capture(captureBuilder.build(), captureListener, mBackgroundHandler);
                    } catch (CameraAccessException e) {
                        e.printStackTrace();
                    }
                }
                @Override
                public void onConfigureFailed(CameraCaptureSession session) {
                }
            }, mBackgroundHandler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
        try {
            dibujarEnCamara(posesOnImage);
        }catch (Exception e){
            Log.d("Error", e.getMessage());
        }
    }

    public void createCameraPreview() {
        try {
            SurfaceTexture texture = textureView.getSurfaceTexture();
            assert texture != null;
            texture.setDefaultBufferSize(imageDimension.getWidth(), imageDimension.getHeight());
            Surface surface = new Surface(texture);
            captureRequestBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
            captureRequestBuilder.addTarget(surface);
            cameraDevice.createCaptureSession(Arrays.asList(surface), new CameraCaptureSession.StateCallback(){
                @Override
                public void onConfigured(@NonNull CameraCaptureSession cameraCaptureSession) {
                    //The camera is already closed
                    if (null == cameraDevice) {
                        return;
                    }
                    // When the session is ready, we start displaying the preview.
                    cameraCaptureSessions = cameraCaptureSession;
                    updatePreview();
                }
                @Override
                public void onConfigureFailed(@NonNull CameraCaptureSession cameraCaptureSession) {
                    Toast.makeText(con, "Configuration change", Toast.LENGTH_SHORT).show();
                }
            }, null);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    public void openCamera() {
        CameraManager manager = (CameraManager) con.getSystemService(Context.CAMERA_SERVICE);
        Log.e(TAG, "is camera open");
        try {
            cameraId = manager.getCameraIdList()[1];
            CameraCharacteristics characteristics = manager.getCameraCharacteristics(cameraId);
            StreamConfigurationMap map = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
            assert map != null;
            imageDimension = map.getOutputSizes(SurfaceTexture.class)[0];
            // Add permission for camera and let user grant the permission
            if (ActivityCompat.checkSelfPermission(con, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(con, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(act, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CAMERA_PERMISSION);
                return;
            }
            manager.openCamera(cameraId, stateCallback, null);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
        Log.e(TAG, "openCamera X");
    }
    protected void updatePreview() {
        if(null == cameraDevice) {
            Log.e(TAG, "updatePreview error, return");
        }
        captureRequestBuilder.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO);
        try {
            cameraCaptureSessions.setRepeatingRequest(captureRequestBuilder.build(), null, mBackgroundHandler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    public void closeCamera() {
        if (null != cameraDevice) {
            cameraDevice.close();
            cameraDevice = null;
        }
        if (null != imageReader) {
            imageReader.close();
            imageReader = null;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                // close the app
                Toast.makeText(con, "Sorry!!!, you can't use this app without granting permission", Toast.LENGTH_LONG).show();
                
            }
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        Log.e(TAG, "onResume");
        startBackgroundThread();
        if (textureView.isAvailable()) {
            openCamera();
        } else {
            textureView.setSurfaceTextureListener(textureListener);
        }
    }
    @Override
    public void onPause() {
        Log.e(TAG, "onPause");
        //closeCamera();
        stopBackgroundThread();
        super.onPause();
    }

    public void dibujarEnCamara(Bitmap bit){
        // imageViewSuper.setBackgroundColor(Color.TRANSPARENT);
        imageView.setImageDrawable(new BitmapDrawable(getResources(), bit));
    }

    public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            mMemoryCache.put(key, bitmap);
        }
    }

    public Bitmap getBitmapFromMemCache(String key) {
        return mMemoryCache.get(key);
    }

    public static MainActivity getInstance() {
        return instance;
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        tryDrawing(holder);

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        tryDrawing(holder);

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    public void tryDrawing(SurfaceHolder holder) {
        Log.i(TAG, "Trying to draw...");

        Canvas canvas = holder.lockCanvas();
        if (canvas == null) {
            Log.e(TAG, "Cannot draw onto the canvas as it's null");
        } else {
            drawMyStuff(canvas);
            holder.unlockCanvasAndPost(canvas);
        }
    }

    public void drawMyStuff(final Canvas canvas) {
        Random random = new Random();
        Log.i(TAG, "Drawing...");
        canvas.drawRGB(255, 128, 128);
    }

    public void comenzar(){
        countDown= new CountDownTimer(leftTime, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.d("RAF", String.valueOf(leftTime));
                leftTime =millisUntilFinished;
                mostrarTiempo();
                //progress = (int) (Start-leftTime);
                //progressBarStatus =progress;
                //pb.setProgress(progressBarStatus);
            }

            @Override
            public void onFinish() {
                // pb.setProgress((int) Start);
                Toast.makeText(getContext(), "Termino", Toast.LENGTH_SHORT).show();
                countDown.cancel();
                start=false;
                calcular();
                if( main.iListaEj<lisEj.size())
                {
                    main.recebirSigEj(lisEj.get(main.iListaEj));
                    main.pasarADescanso();
                }else {

                    main.pasarArta();
                }

            }
        }.start();

    }
    public void cargarDatos()
    {
        //lisEj.get(main.iListaEj).get_Foto()
        Double time;
        // imgE.setImageDrawable(lisEj.get(main.iListaEj).get_Foto());
        txtNombre.setText(lisEj.get(main.iListaEj).get_NombreEjercicio());
        time=(lisEj.get(main.iListaEj).get_Seg() + 20)* 1000;
        Start=time.longValue();
        leftTime=Start ;
        //comenzar();
        Picasso.with(con).load(lisEj.get(main.iListaEj).get_Foto()).into(imgE);
        main.iListaEj++;

    }
    public void mostrarTiempo()
    {
        int minutes = (int) (leftTime/1000)/60;
        int seconds = (int) (leftTime/1000)%60;
        String der= String.format(Locale.getDefault(),"%02d:%02d",minutes,seconds);
        txtTiempo.setText(der);
    }
    public void calcular()
    {
        float total = acumB + acumM;
        float prom = acumB/total;
        float resultado = 1000 * prom;
        Log.d("RTA", String.valueOf(total));
        Log.d("RTA", String.valueOf(prom));
        Log.d("RTA", String.valueOf(resultado));
    }
}
