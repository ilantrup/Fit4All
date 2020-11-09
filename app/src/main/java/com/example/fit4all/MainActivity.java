package com.example.fit4all;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;

import com.facebook.CallbackManager;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class MainActivity extends Activity {
    private static final int RC_SIGN_IN = 123;
    FragmentManager manager;
    FragmentTransaction transacFrag;

    Boolean entroConGoogle = false;
    Boolean finRut;
    String nombreNoSeguro;
    String apellidoNoSeguro;

    FirebaseUser user;

    ArrayList<Ejercicio> Listadeejs = new ArrayList<>();

    int iListaEj;
    Ejercicio sigEj;

    zonaDeEjercicio zonaa= new zonaDeEjercicio();

    ArrayList<Ejercicio> arrayTodosLosEstiramientos;

    ArrayList<Ejercicio> arrayEstiramientosInf;
    ArrayList<Ejercicio> arrayEstiramientosMed;
    ArrayList<Ejercicio> arrayEstiramientosSup;

    private ArrayList<String> calentamientoSup;
    private ArrayList<String> calentamientoMed;
    private ArrayList<String> calentamientoInf;



    Boolean entroxPrimeraVez = null;

    String[] nameUsuario;

    String UIDUSR;

    Uri ur;

    BottomNavigationView nav;
    //AuthViewModelBase auth;
    Usuario usuarioActivo;
    plato plat = new plato();
    boolean TodosPermisos;
    CallbackManager callbackManager;

    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    zonaDeEjercicio zona = new zonaDeEjercicio();


    Usuario usuarioACrear;

    //database

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ArrayList<Ejercicio> ListaADevolver;

    private ArrayList<Ejercicio> ListaDevolverCompleta;
    zonaDeEjercicio zonaSuperior= new zonaDeEjercicio();
    zonaDeEjercicio zonaMedia= new zonaDeEjercicio();
    zonaDeEjercicio zonaInferior= new zonaDeEjercicio();



    ArrayList<Ejercicio> ListaSup = new ArrayList<>();
    ArrayList<Ejercicio> ListaMed = new ArrayList<>();
    ArrayList<Ejercicio> ListaInf = new ArrayList<>();

    ArrayList<Ejercicio> ListaEjer = new ArrayList<>();

    public ArrayList<Ejercicio> id= new ArrayList<Ejercicio>();
    public ArrayList<String> Listade3Ejs = new ArrayList<>();
    public ArrayList<Ejercicio> Ej = new ArrayList<Ejercicio>();
    public ArrayList<tipoEvento> events = new ArrayList<>();
    public ArrayList<logro> arrLogro = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        arrayTodosLosEstiramientos = new ArrayList<>();
        arrayEstiramientosInf = new ArrayList<>();
        arrayEstiramientosMed = new ArrayList<>();
        arrayEstiramientosSup = new ArrayList<>();

        calentamientoInf = new ArrayList<>();
        calentamientoMed = new ArrayList<>();
        calentamientoSup = new ArrayList<>();



        manager = getFragmentManager();

        listaIdDeEjerciciosSegunZona("Superior");
        listaIdDeEjerciciosSegunZona("Inferior");
        listaIdDeEjerciciosSegunZona("Medio");


        zonaSuperior.set_img(getResources().getDrawable(R.drawable.rutinaprin));
        zonaSuperior.set_idZonaDeEjercicio("Superior");
        zonaMedia.set_img(getResources().getDrawable(R.drawable.rutinaprin));
        zonaMedia.set_idZonaDeEjercicio("Media");
        zonaInferior.set_img(getResources().getDrawable(R.drawable.rutinaprin));
        zonaInferior.set_idZonaDeEjercicio("Inferior");

        iListaEj=0;

        finRut=false;
        usuarioACrear = new Usuario();

        traerTodosLosEstiramientos();

        prefs = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        editor = prefs.edit();

        String uid = prefs.getString("UID", "");

        if(uid =="") {
            pasarAingresodeuser();
        }
        else{
            guardarInfoUsuarioActivo(uid);

        }




/*
        // Escribir en BD
        // Create a new user with a first and last name
        Map<String, Object> user = new HashMap<>();
        user.put("first", "Federico");
        user.put("last", "Kozak");
// Add a new document with a generated ID
        db.collection("usuarios")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("TAG", "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("TAG", "Error adding document", e);
                    }
                });
        // Leer info de BD
        db.collection("usuarios")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("TAG", document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.d("TAG", "Error getting documents.", task.getException());
                        }
                    }
                });
 */

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }

/*
        zonaa=devolverZona();
        Log.d("Fede", String.valueOf(zonaa.get_idZonaDeEjercicio()));
        if(zonaa.get_idZonaDeEjercicio()=="Superior")
        {
            id=devolverListaInferior();
        }
        else
        {
            id=devolverListaSuperior();
        }



        Listade3Ejs = ListaDe3Ejs();
        if (Listade3Ejs == null || Listade3Ejs.size() <1){
            Ej=randomEjerId(id);
        }
        else {
            //Aca va el traer ejercicio segun id



            traerEjSegunId();





        }

 */



        //Entradas en calor

        calentamientoSup.add("28");
        calentamientoSup.add("29");
        calentamientoSup.add("30");
        calentamientoSup.add("1");
        calentamientoSup.add("2");
        calentamientoSup.add("3");

        calentamientoMed.add("28");
        calentamientoMed.add("29");
        calentamientoMed.add("30");
        calentamientoMed.add("7");
        calentamientoMed.add("9");
        calentamientoMed.add("10");

        calentamientoInf.add("28");
        calentamientoInf.add("29");
        calentamientoInf.add("30");
        calentamientoInf.add("12");
        calentamientoInf.add("13");
        calentamientoInf.add("14");
        calentamientoInf.add("15");
        zonaSuperior.set_finish(traerBooleanSup());
        zonaInferior.set_finish(traerBooleanInf());
        zonaMedia.set_finish(traerBooleanMed());
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build();
        db.setFirestoreSettings(settings);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for (int i = 0; i < permissions.length; i++) {
            if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                TodosPermisos = false;
            } else {
                TodosPermisos = true;
            }
        }
    }

    public static Context contextOfApplication;
    public static Context getContextOfApplication()
    {
        return contextOfApplication;
    }


    void pasarAingresodeuser() {
        Fragment fragingresodeuser;
        fragingresodeuser = new fragmentInicio();
        transacFrag = manager.beginTransaction();
        transacFrag.replace(R.id.frameHolder, fragingresodeuser);
        transacFrag.addToBackStack(null).commit();
    }


    void pasarAregister() {
        Fragment fragSexo;
        fragSexo = new fragmentSexo();
        transacFrag = manager.beginTransaction();
        transacFrag.replace(R.id.frameHolder, fragSexo);
        transacFrag.addToBackStack(null).commit();
    }

    void pasarAaltura() {

        Fragment fragAlt;
        fragAlt = new fragAltura();
        transacFrag = manager.beginTransaction();
        transacFrag.replace(R.id.frameHolder, fragAlt);
        transacFrag.addToBackStack(null).commit();
    }

    void pasarApeso() {
        Fragment fragPes;
        fragPes = new fragPeso();
        transacFrag = manager.beginTransaction();
        transacFrag.replace(R.id.frameHolder, fragPes);
        transacFrag.addToBackStack(null).commit();
    }

    void pasarAedad() {
        Fragment fragEdad;
        fragEdad = new fragEdad();
        transacFrag = manager.beginTransaction();
        transacFrag.replace(R.id.frameHolder, fragEdad);
        transacFrag.addToBackStack(null).commit();
    }

    void pasarAdedicacion() {
        Fragment fragDed;
        fragDed = new fragDedicacion();
        transacFrag = manager.beginTransaction();
        transacFrag.replace(R.id.frameHolder, fragDed);
        transacFrag.addToBackStack(null).commit();
    }

    void pasarAobjetivo() {
        Fragment fragObj;
        fragObj = new fragObjetivo();
        transacFrag = manager.beginTransaction();
        transacFrag.replace(R.id.frameHolder, fragObj);
        transacFrag.addToBackStack(null).commit();
    }

    void pasarAfoto() {
        Fragment fragFot;
        fragFot = new fragIngresoFoto();
        transacFrag = manager.beginTransaction();
        transacFrag.replace(R.id.frameHolder, fragFot);
        transacFrag.addToBackStack(null).commit();
    }

    void pasarAcita() {
        Fragment fragCita;
        fragCita = new fragIngresoCita();
        transacFrag = manager.beginTransaction();
        transacFrag.replace(R.id.frameHolder, fragCita);
        transacFrag.addToBackStack(null).commit();
    }


    void pasarAregistroDatos() {
        Fragment fragDatos;
        fragDatos = new fragmenteRegistroDatos();
        transacFrag = manager.beginTransaction();
        transacFrag.replace(R.id.frameHolder, fragDatos);
        transacFrag.addToBackStack(null).commit();
    }

    void pasarAcompletado() {
        Fragment fragComplet;
        fragComplet = new FragmentCompletado();
        transacFrag = manager.beginTransaction();
        transacFrag.replace(R.id.frameHolder, fragComplet);
        transacFrag.addToBackStack(null).commit();
    }

    void pasarAonboraingrango() {
        Fragment fragOrang;
        fragOrang = new fragmenOnboardingRango();
        transacFrag = manager.beginTransaction();
        transacFrag.replace(R.id.frameHolder, fragOrang);
        transacFrag.addToBackStack(null).commit();
    }

    void pasarAonboraingBloq() {
        Fragment fragBloq;
        fragBloq = new fragmentOnboardingBloq();
        transacFrag = manager.beginTransaction();
        transacFrag.replace(R.id.frameHolder, fragBloq);
        transacFrag.addToBackStack(null).commit();
    }

    void pasarAonboraingCom() {
        Fragment fragCom;
        fragCom = new fragmentOnboardingComida();
        transacFrag = manager.beginTransaction();
        transacFrag.replace(R.id.frameHolder, fragCom);
        transacFrag.addToBackStack(null).commit();
    }

    void pasarAPrin() {
        Fragment fragPrin;
        fragPrin = new fragmentPaginaPrincipal();
        transacFrag = manager.beginTransaction();
        transacFrag.replace(R.id.frameHolderDelNavBar, fragPrin);
        transacFrag.addToBackStack(null).commit();
    }

    void pasarAcomida() {

        Fragment fragCom;
        fragCom = new fragmentComida();
        transacFrag = manager.beginTransaction();
        transacFrag.replace(R.id.frameHolderDelNavBar, fragCom);
        transacFrag.addToBackStack(null).commit();
    }
    void pasarARutina() {
        Fragment fragRut;
        fragRut = new fragmentListaRutina();
        transacFrag = manager.beginTransaction();
        transacFrag.replace(R.id.frameHolderDelNavBar, fragRut);
        transacFrag.addToBackStack(null).commit();
    }
    void pasarAejercicio()
    {
        Fragment fragEje;
        fragEje = new fragmentListaEjere();
        transacFrag = manager.beginTransaction();
        transacFrag.replace(R.id.frameHolder, fragEje);
        transacFrag.addToBackStack(null).commit();
    }

    void pasarASerieEjer()
    {
        Fragment fragSerEje;
        fragSerEje = new fragmentSerieEjercicios();
        transacFrag = manager.beginTransaction();
        transacFrag.replace(R.id.frameHolder, fragSerEje);
        transacFrag.addToBackStack(null).commit();
    }

    void  pasarArta(){

        Fragment fragRta;
        fragRta = new fragResultadosEj();
        transacFrag = manager.beginTransaction();
        transacFrag.replace(R.id.frameHolder, fragRta);
        transacFrag.addToBackStack(null).commit();
    }
    void  pasarADescanso(){
        Fragment fragDes;
        fragDes = new fragDescanso();
        transacFrag = manager.beginTransaction();
        transacFrag.replace(R.id.frameHolder, fragDes);
        transacFrag.addToBackStack(null).commit();
    }

    //ArrayList<EventDay> devolverArrayCal(){return events;}

    //void recebirCal (ArrayList<EventDay> Ev) { events= Ev; }

    ArrayList<tipoEvento> devolverArrayCal(){return events;}

    void recibiArrayEj(ArrayList<Ejercicio> ej)
    {
        ListaEjer= ej;
    }

    ArrayList<Ejercicio> devolverArrayEj()
    {
        return ListaEjer;
    }

    void pasarAEditar() {

        Fragment fragEd;
        fragEd = new fragPerfilEditar();
        transacFrag = manager.beginTransaction();
        transacFrag.replace(R.id.frameHolderDelNavBar, fragEd);
        transacFrag.addToBackStack(null).commit();
    }

    void pasarALogros() {

        Fragment fragLog;
        fragLog = new fragPerfilLogros();
        transacFrag = manager.beginTransaction();
        transacFrag.replace(R.id.frameHolderDelNavBar, fragLog);
        transacFrag.addToBackStack(null).commit();
    }

    void pasarATrenSuperior() {

        Fragment fragSup;
        fragSup = new fragListaTipoEjerciciosSuperior();
        transacFrag = manager.beginTransaction();
        transacFrag.replace(R.id.frameHolderDelNavBar, fragSup);
        transacFrag.addToBackStack(null).commit();
    }


    void pasarAPlato() {

        Fragment fragPlato;
        fragPlato = new fragPlato();
        transacFrag = manager.beginTransaction();
        transacFrag.replace(R.id.frameHolderDelNavBar, fragPlato);
        transacFrag.addToBackStack(null).commit();
    }

    void pasarANav() {

        Fragment fragNav;
        fragNav = new fragNavbar();
        transacFrag = manager.beginTransaction();
        transacFrag.replace(R.id.frameHolder, fragNav);
        transacFrag.addToBackStack(null).commit();
    }

    void pasarAPerfil() {
        Fragment fragPer;
        fragPer = new fragPerfil();
        transacFrag = manager.beginTransaction();
        transacFrag.replace(R.id.frameHolderDelNavBar, fragPer);
        transacFrag.addToBackStack(null).commit();
    }

    void  pasarAcalendario()
    {
        Fragment fragCal;
        fragCal = new fragCalendario();
        transacFrag = manager.beginTransaction();
        transacFrag.replace(R.id.frameHolderDelNavBar, fragCal);
        transacFrag.addToBackStack(null).commit();
    }

    void  pasarFragEjcam()
    {
        Fragment fragEjCam;
        fragEjCam = new fragEjCamera();
        transacFrag = manager.beginTransaction();
        transacFrag.replace(R.id.frameHolder, fragEjCam);
        transacFrag.addToBackStack(null).commit();
    }



    void recebirDatosUnplato(plato unplato) {
        plat = unplato;
    }

    plato traerDatos() {
        return plat;
    }

    void alertaIngresoIncorrecto() {
        AlertDialog.Builder mensaje;
        mensaje = new AlertDialog.Builder(this);
        mensaje.setMessage("El ingreso es incorrecto");
        mensaje.setTitle("Ingreso de datos");
        mensaje.setPositiveButton("Aceptar", null);
        mensaje.create();
        mensaje.show();
    }

    void alertaNoIngreso() {
        AlertDialog.Builder mensaje;
        mensaje = new AlertDialog.Builder(this);
        mensaje.setMessage("Falta el ingreso de datos");
        mensaje.setTitle("Ingreso de datos");
        mensaje.setPositiveButton("Aceptar", null);
        mensaje.create();
        mensaje.show();
    }

    void alertaUsuarioNoVerificado() {
        AlertDialog.Builder mensaje;
        mensaje = new AlertDialog.Builder(this);
        mensaje.setMessage("El usuario no esta verificado");
        mensaje.setTitle("Error en usuario");
        mensaje.setPositiveButton("Aceptar", null);
        mensaje.create();
        mensaje.show();
    }


    public void createSignInMailIntent() {
        // [START auth_fui_create_intent]
        // Choose authentication providers
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build());


        // Create and launch sign-in intent
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(),
                RC_SIGN_IN);
        // [END auth_fui_create_intent]
    }


    public void createSignInGoogleIntent() {
        // [START auth_fui_create_intent]
        // Choose authentication providers
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.GoogleBuilder().build());
        entroConGoogle = true;

        // Create and launch sign-in intent
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(),
                RC_SIGN_IN);
        // [END auth_fui_create_intent]
    }


    // [START auth_fui_result]
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                user = FirebaseAuth.getInstance().getCurrentUser();

                UIDUSR = user.getUid();

                ur = user.getPhotoUrl();
                String[] nomCompleto;
                nomCompleto = cortarCadenaPorEspacio(user.getDisplayName());
                nombreNoSeguro = nomCompleto[0];
                apellidoNoSeguro = "";
                for (int i = 1; i<nomCompleto.length; i++){
                    apellidoNoSeguro = apellidoNoSeguro + nomCompleto[i] + "";
                }




                boolean ver = user.isEmailVerified();
                if (ver) {
                    guardarInfoUsuarioActivo(UIDUSR);

                } else {
                    alertaUsuarioNoVerificado();
                }


            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...

                alertaIngresoIncorrecto();
            }

        }

    }
    // [END auth_fui_result]


    public void guardarInfoUsuarioActivo(final String UID) {


        db.collection("usuarios")
                .whereEqualTo("UID", UID)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                                           @Override
                                           public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                               entroxPrimeraVez = true;
                                               if (task.isSuccessful()) {
                                                   //cuando es correcto el ingreso
                                                   for (QueryDocumentSnapshot document1 : task.getResult()) {
                                                       Log.d("TAG", document1.getId() + " => " + document1.getData());
                                                       String idusuario = document1.getId();
                                                       String nombre = document1.getString("Nombre");
                                                       String apellido = document1.getString("Apellido");
                                                       String sexo = document1.getString("Sexo");
                                                       Date edad = document1.getDate("Edad");
                                                       Double altura = document1.getDouble("Altura");
                                                       Double peso = document1.getDouble("Peso");
                                                       String tipoalimentacion = document1.getString("Tipo_De_Alimentacion");
                                                       String idexperiencia = document1.getString("idExperiencia");
                                                       Boolean modolesion = document1.getBoolean("Modo_Lesion");
                                                       String foto = document1.getString("Foto");
                                                       String cita = document1.getString("Cita");
                                                       String idcalendario = document1.getString("idCalendario");
                                                       List<String> logros = (List<String>) document1.get("Logros");
                                                       Double ded = document1.getDouble("Dedicacion");
                                                       String obj = document1.getString("Objetivo");
                                                       Double xpMed = document1.getDouble("XPMedio");
                                                       Double xpSup = document1.getDouble("XPSuperior");
                                                       Double xpInf = document1.getDouble("XPInferior");




                                                       usuarioActivo = new Usuario();
                                                       usuarioActivo.set_idUsuario(idusuario);
                                                       usuarioActivo.set_Nombre(nombre);
                                                       usuarioActivo.set_Apellido(apellido);
                                                       usuarioActivo.set_Sexo(sexo);
                                                       usuarioActivo.set_Edad(edad);
                                                       usuarioActivo.set_Altura(altura);
                                                       usuarioActivo.set_Peso(peso);
                                                       usuarioActivo.set_TipoAlimentacion(tipoalimentacion);
                                                       usuarioActivo.set_idExperiencia(idexperiencia);
                                                       usuarioActivo.set_ModoLesion(modolesion);
                                                       usuarioActivo.set_Foto(foto);
                                                       usuarioActivo.set_Cita(cita);
                                                       usuarioActivo.set_idCalendario(idcalendario);
                                                       usuarioActivo.set_Logros(logros);
                                                       usuarioActivo.set_Dedicacion(ded);
                                                       usuarioActivo.set_Objetivo(obj);
                                                       usuarioActivo.set_xpInferior(xpInf);
                                                       usuarioActivo.set_xpSuperior(xpSup);
                                                       usuarioActivo.set_xpMedio(xpMed);


                                                       editor.putString("UID", UID);
                                                       editor.commit();

                                                       entroxPrimeraVez = false;
                                                       traerEventos();
                                                       traerLogros();

                                                   }
                                               } else {

                                                   alertaIngresoIncorrecto();

                                                   Log.d("TAG", "Error getting documents: " + task.getException());
                                               }
                                               if (entroxPrimeraVez == null) {
                                                   alertaIngresoIncorrecto();
                                               } else if (!entroxPrimeraVez) {
                                                   pasarANav();
                                               } else {
                                                   pasarAregister();
                                               }
                                           }
                                       }

                );
    }


    public Usuario devolverUsuarioActivo() {
        return usuarioActivo;
    }

    public void setUsuarioActivo(Usuario usr) {
        usuarioActivo = usr;
    }


    public void setUsuarioACrear(Usuario usr) {
        usuarioACrear = usr;
    }

    public Usuario devolverUsuarioACrear() {
        return usuarioACrear;
    }


    public static java.util.Date getDateFromDatePicker(DatePicker datePicker) {
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year = datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        return calendar.getTime();
    }


    public void cargarUsuarioEnBD() {

        Usuario usuarioACargar = new Usuario();
        usuarioACargar = devolverUsuarioACrear();


        // Add a new document with a generated id.
        Map<String, Object> data = new HashMap<>();
        data.put("Altura", usuarioACargar.get_Altura());
        data.put("Apellido", usuarioACargar.get_Apellido());
        data.put("Cita", usuarioACargar.get_Cita());
        data.put("Dedicacion", usuarioACargar.get_Dedicacion());
        data.put("Edad", usuarioACargar.get_Edad());
        if(entroConGoogle) {
            data.put("Foto", ur.toString());
        } else{
            data.put("Foto", "");

        }
        data.put("Logros", usuarioACargar.get_Logros());
        data.put("Modo_Lesion", usuarioACargar.get_ModoLesion());
        data.put("Nombre", usuarioACargar.get_Nombre());
        data.put("Objetivo", usuarioACargar.get_Objetivo());
        data.put("Peso", usuarioACargar.get_Peso());
        data.put("Sexo", usuarioACargar.get_Sexo());
        data.put("Tipo_De_Alimentacion", usuarioACargar.get_TipoAlimentacion());
        data.put("UID", UIDUSR);
        data.put("idCalendario", usuarioACargar.get_idCalendario());
        data.put("idExperiencia", usuarioACargar.get_idExperiencia());
        data.put("XPMedio",0.0);
        data.put("XPSuperior",0.0);
        data.put("XPInferior",0.0);



        db.collection("usuarios")
                .add(data)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        // Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                        Log.d("CargarUsuarioABD", "Se cargo exitosamente");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("CargarUsuarioABD", "Error agregando usuario a BD" + e);

                        // Log.w(TAG, "Error adding document", e);
                    }
                });

    }


/*
    public void logout(View view){
        LoginManager.getInstance().logOut();
       pasarAingresodeuser();
    }
*/

    public String[] cortarCadenaPorEspacio(String cadena) {
        return cadena.split("\\ ");
    }

    public void cambiarDatos(Usuario usr) {


        Map<String, Object> data = new HashMap<>();
        data.put("Altura", usr.get_Altura());
        data.put("Apellido", usr.get_Apellido());
        data.put("Cita", usr.get_Cita());
        data.put("Dedicacion", usr.get_Dedicacion());
        data.put("Edad", usr.get_Edad());
        data.put("Foto", usr.get_Foto());
        data.put("Logros", usr.get_Logros());
        data.put("Modo_Lesion", usr.get_ModoLesion());
        data.put("Nombre", usr.get_Nombre());
        data.put("Objetivo", usr.get_Objetivo());
        data.put("Peso", usr.get_Peso());
        data.put("Sexo", usr.get_Sexo());
        data.put("Tipo_De_Alimentacion", usr.get_TipoAlimentacion());
        data.put("UID", UIDUSR);
        data.put("idCalendario", usr.get_idCalendario());
        data.put("idExperiencia", usr.get_idExperiencia());
        data.put("XPMedio", usr.get_xpMedio());
        data.put("XPSuperior",usr.get_xpSuperior());
        data.put("XPInferior",usr.get_xpInferior());
        db.collection("usuarios").document(usr.get_idUsuario()).update(data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Editar", "DocumentSnapshot successfully updated!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Editar", "Error updating document", e);
                    }
                });
    }


    public static Integer getAge(int yearOfBirth, int monthOfBirth, int dayOfBirth) {

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalDate today = LocalDate.now();
            LocalDate birthdate = LocalDate.of(yearOfBirth, monthOfBirth, dayOfBirth);
            Period p = Period.between(birthdate, today);
            return p.getYears();
        }else{
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH) + 1;
            int day = cal.get(Calendar.DAY_OF_MONTH);

            Calendar c2 = new GregorianCalendar(yearOfBirth,monthOfBirth,dayOfBirth);
            Calendar c1 =  new GregorianCalendar(year, month, day);

            long end = c2.getTimeInMillis();
            long start = c1.getTimeInMillis();

            long milliseconds = TimeUnit.MILLISECONDS.toMillis(Math.abs(end - start));

            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(milliseconds);
            int mYear = c.get(Calendar.YEAR)-1970;

            return mYear;
        }
    }


    public static Calendar toCalendar(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

    public void cerrarSesion(){
        editor.putString("UID", "");
        editor.commit();
        pasarAingresodeuser();

    }

    public String randomSupInf (){
        final Random generador = new Random();
        final Integer[] preguntas = {1,2};

        int pregunta = preguntas[generador.nextInt(preguntas.length)];
        String p = String.valueOf(pregunta);


        if (p.equals("1")){
            return "Superior";
        }
        else{
            return "Inferior";
        }
    }


    public void listaIdDeEjerciciosSegunZona(final String Zona) {

        ListaADevolver = new ArrayList<>();

        db.collection("zonaDeEjercicios")
                .whereEqualTo("NombreZona", Zona)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                            //cuando es correcto el ingreso
                            for (QueryDocumentSnapshot document1 : task.getResult()) {
                                Log.d("TAG", "DocumentSnapshot data: " + document1.getData());

                                List<String> l = (List<String>) document1.get("Ejercicios");

                                if(Zona == "Superior") {
                                    listaDeEjercicios(l, "Superior");
                                }
                                if(Zona == "Inferior") {
                                    listaDeEjercicios(l, "Inferior");
                                }
                                if(Zona == "Medio") {
                                    listaDeEjercicios(l, "Medio");
                                }

                            }
                        } else {
                            Log.d("TAG", "No such document");
                        }
                    }
                });


    }


    public void listaDeEjercicios(List<String> ListaID, final String Z){
        int i;
        ListaDevolverCompleta = new ArrayList<>();
        for(i = 0; i < ListaID.size(); i++){

            String idEj = ListaID.get(i);


            DocumentReference docRef = db.collection("Ejercicios").document(idEj);
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            Log.d("TAG", "DocumentSnapshot data: " + document.getData());

                            Ejercicio ej = new Ejercicio();
                            ej.set_NombreEjercicio(document.getString("NombreEjercicio"));
                            ej.set_Destreza(document.getString("Destreza"));
                            ej.set_Dificultad(document.getDouble("Dificultad"));
                            ej.set_Foto(document.getString("Foto"));
                            ej.set_seg(document.getDouble("Segundos"));

                            List<String> mus = (List<String>) document.get("Musculos");
                            ej.set_Musculos(mus);
                            ej.setIdEjercicio(document.getId());

                            if(Z == "Medio"){
                                ListaMed.add(ej);
                            }
                            if(Z == "Superior"){
                                ListaSup.add(ej);
                            }
                            if(Z == "Inferior"){
                                ListaInf.add(ej);
                            }





                        } else {
                            Log.d("TAG", "No such document");
                        }
                    } else {
                        Log.d("TAG", "get failed with ", task.getException());
                    }
                }
            });


        }

    }
    public ArrayList<Ejercicio> randomEjerId(ArrayList<Ejercicio> todoID)
    {
        ArrayList<Ejercicio> nuevaLista= new ArrayList<>();
        Random random = new Random();
        int index;
        Set<String> set = new HashSet<>();

        while (nuevaLista.size()!=3)
        {
            index = random.nextInt(todoID.size());
            nuevaLista.add(todoID.get(index));
            set.add(todoID.get(index).getIdEjercicio());
            todoID.remove(index);
        }


        editor.putStringSet("ListaIdEjs",set);
        editor.commit();

        return nuevaLista;
    }


    public ArrayList<Ejercicio> randomEjerIdMedio(ArrayList<Ejercicio> todoID)
    {
        ArrayList<Ejercicio> nuevaLista= new ArrayList<>();
        Random random = new Random();
        int index;
        Set<String> set = new HashSet<>();

        while (nuevaLista.size()!=3)
        {
            index = random.nextInt(todoID.size());
            nuevaLista.add(todoID.get(index));
            set.add(todoID.get(index).getIdEjercicio());
            todoID.remove(index);
        }


        editor.putStringSet("ListaIdEjsMedio",set);
        editor.commit();

        return nuevaLista;
    }

    public ArrayList<Ejercicio> devolverListaMedio (){ return ListaMed;}
    public ArrayList<Ejercicio> devolverListaSuperior (){ return ListaSup;}
    public ArrayList<Ejercicio> devolverListaInferior (){ return ListaInf;}
    public zonaDeEjercicio devolverZona()
    {
        return zona;
    }
    public void recebirZona(zonaDeEjercicio zon)
    {
        zona=zon;
    }
    public zonaDeEjercicio devolverSup(){return zonaSuperior;}
    public zonaDeEjercicio devolverInf(){return zonaInferior;}
    public zonaDeEjercicio devolverMed(){return zonaMedia;}


    public Integer devolverDiaDeLaSemana (){
        Calendar cal = Calendar.getInstance();
        Integer dia = cal.get(Calendar.DAY_OF_WEEK);
        return dia;
    }


    public Integer random1a5 (){
        int random = new Random().nextInt(5) + 1;

        return random;
    }


    public String devolverNombreNoSeguro(){return nombreNoSeguro;}
    public String devolverApellidoNoSeguro(){return apellidoNoSeguro;}






    public ArrayList<String> ListaDe3Ejs(){
        ArrayList<String> lista;
        Set<String> fetch = prefs.getStringSet("ListaIdEjs", null);
        if (fetch != null) {
            lista = new ArrayList<>(fetch);
        }
        else {
            lista = null;
        }
        return lista;
    }

    public ArrayList<String> ListaDe3EjsMedio(){
        ArrayList<String> lista;
        Set<String> fetch = prefs.getStringSet("ListaIdEjsMedio", null);
        if (fetch != null) {
            lista = new ArrayList<>(fetch);
        }
        else {
            lista = null;
        }
        return lista;
    }


    public Ejercicio traerEjSegunId (String id){

        Ejercicio ejADevolver = new Ejercicio();

   /* for (int i = 0; i < ListaDe3Ejs().size(); i++) {


        DocumentReference docRef = db.collection("Ejercicios").document(ListaDe3Ejs().get(i));
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("TAG", "DocumentSnapshot data: " + document.getData());
                        Ejercicio ej = new Ejercicio();

                        ej.set_NombreEjercicio(document.getString("NombreEjercicio"));
                        ej.set_Destreza(document.getString("Destreza"));
                        ej.set_Dificultad(document.getDouble("Dificultad"));
                        ej.set_Foto(document.getString("Foto"));


                        Listadeejs.add(ej);

                        Log.d("traerEjSegunId", "traje el ej" + ej.get_NombreEjercicio());

                    } else {
                        Log.d("TAG", "No such document");
                    }
                } else {
                    Log.d("TAG", "get failed with ", task.getException());
                }
            }
        });

    }
    */
        int j = 0, i = 1;
        boolean encontro = false;

        while (i <= 3 && encontro == false){

            if (i == 1){
                while(encontro == false && j < ListaSup.size()){
                    if ( ListaSup.get(j).getIdEjercicio().equals(id)){
                        encontro=true;
                        ejADevolver = ListaSup.get(j);

                    } else{j++;}
                }
            }

            if (i == 2){
                j=0;
                while(encontro == false && j < ListaMed.size()){
                    if (ListaMed.get(j).getIdEjercicio().equals(id)){
                        encontro=true;
                        ejADevolver = ListaMed.get(j);

                    } else{j++;}
                }
            }

            if (i == 3){
                j=0;
                while(encontro == false && j < ListaInf.size()){
                    if (ListaInf.get(j).getIdEjercicio().equals(id)){
                        encontro=true;
                        ejADevolver = ListaInf.get(j);

                    } else{j++;}
                }
            }

            i++;
        }

        return ejADevolver;
    }

    public void reiniciarListaDeEjs(){
        Set<String> set = new HashSet<>();
        editor.putStringSet("ListaIdEjs",set);
        editor.commit();
    }

    public void reiniciarListaDeEjsMedio(){
        Set<String> set = new HashSet<>();
        editor.putStringSet("ListaIdEjs",set);
        editor.commit();
    }
    public boolean finalizarRutina()
    {
        if(zonaInferior.get_finish()==true && zonaMedia.get_finish()==true)
        {
            finRut=true;
            if(!traerBooleanPrimeraVez()){
                setearPrimeraVezTrue();
                java.util.Date fecha = new Date();
                cargarEventoBD(fecha, true);
            }
        }
        else if(zonaSuperior.get_finish()==true && zonaMedia.get_finish()==true)
        {
            finRut=true;
            if(!traerBooleanPrimeraVez()){
                setearPrimeraVezTrue();
                java.util.Date fecha = new Date();
                cargarEventoBD(fecha, true);
            }
        }
        return  finRut;
    }

    public void guardarDiaActual(){
        Calendar fechaAct = Calendar.getInstance();
        int dia = fechaAct.get(Calendar.DAY_OF_MONTH);
        int mes = fechaAct.get(Calendar.MONTH);
        int anio = fechaAct.get(Calendar.YEAR);

        editor.putInt("UltDia", dia);
        editor.putInt("UltMes", mes);
        editor.putInt("UltAnio", anio);
        editor.commit();
    }

    public boolean compararUltFecha(){
        boolean esMismoDia;
        Calendar fechaAct = Calendar.getInstance();
        int dia = fechaAct.get(Calendar.DAY_OF_MONTH);
        int mes = fechaAct.get(Calendar.MONTH);
        int anio = fechaAct.get(Calendar.YEAR);

        int ultdia = prefs.getInt("UltDia", 0);
        int ultmes = prefs.getInt("UltMes", 0);
        int ultanio = prefs.getInt("UltAnio", 0);

        if (ultdia != dia || ultmes != mes || ultanio!= anio){
            guardarDiaActual();
            esMismoDia = false;
        }
        else{
            esMismoDia = true;
        }

        return esMismoDia;
    }

    public void recebirSigEj(Ejercicio sigEjj)
    {
        sigEj=sigEjj;
    }
    public Ejercicio devolverSigEj()
    {
        return  sigEj;
    }


    public void traerTodosLosEstiramientos(){


        Integer i;
        ListaDevolverCompleta = new ArrayList<>();
        for(i = 1; i <= 18; i++){

            String idEs = i.toString();


            DocumentReference docRef = db.collection("estiramiento").document(idEs);
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            Log.d("TAG", "DocumentSnapshot data: " + document.getData());

                            Ejercicio es = new Ejercicio();
                            es.setIdEjercicio(document.getId());
                            es.set_Foto(document.getString("Foto"));
                            ArrayList<String> mus = new ArrayList<>();
                            mus.add(document.getString("Musculo"));
                            es.set_Musculos(mus);

                            if(document.getString("Zona").equals("Superior")){
                                arrayEstiramientosSup.add(es);
                            }
                            if(document.getString("Zona").equals("Media")){
                                arrayEstiramientosMed.add(es);
                            }
                            if(document.getString("Zona").equals("Inferior")){
                                arrayEstiramientosInf.add(es);
                            }

                            arrayTodosLosEstiramientos.add(es);





                        } else {
                            Log.d("TAG", "No such document");
                        }
                    } else {
                        Log.d("TAG", "get failed with ", task.getException());
                    }
                }
            });


        }

    }



    public Ejercicio traerEstiramientoSegunId (String id){

        Ejercicio esADevolver = new Ejercicio();

   /* for (int i = 0; i < ListaDe3Ejs().size(); i++) {


        DocumentReference docRef = db.collection("Ejercicios").document(ListaDe3Ejs().get(i));
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("TAG", "DocumentSnapshot data: " + document.getData());
                        Ejercicio ej = new Ejercicio();

                        ej.set_NombreEjercicio(document.getString("NombreEjercicio"));
                        ej.set_Destreza(document.getString("Destreza"));
                        ej.set_Dificultad(document.getDouble("Dificultad"));
                        ej.set_Foto(document.getString("Foto"));


                        Listadeejs.add(ej);

                        Log.d("traerEjSegunId", "traje el ej" + ej.get_NombreEjercicio());

                    } else {
                        Log.d("TAG", "No such document");
                    }
                } else {
                    Log.d("TAG", "get failed with ", task.getException());
                }
            }
        });

    }
    */
        int j = 0, i = 1;
        boolean encontro = false;

        while (i <= 3 && encontro == false){

            if (i == 1){
                while(encontro == false && j < arrayEstiramientosSup.size()){
                    if ( arrayEstiramientosSup.get(j).getIdEjercicio().equals(id)){
                        encontro=true;
                        esADevolver = arrayEstiramientosSup.get(j);

                    } else{j++;}
                }
            }

            if (i == 2){
                j=0;
                while(encontro == false && j < arrayEstiramientosMed.size()){
                    if (arrayEstiramientosMed.get(j).getIdEjercicio().equals(id)){
                        encontro=true;
                        esADevolver = arrayEstiramientosMed.get(j);

                    } else{j++;}
                }
            }

            if (i == 3){
                j=0;
                while(encontro == false && j < arrayEstiramientosInf.size()){
                    if (arrayEstiramientosInf.get(j).getIdEjercicio().equals(id)){
                        encontro=true;
                        esADevolver = arrayEstiramientosInf.get(j);

                    } else{j++;}
                }
            }

            i++;
        }

        return esADevolver;
    }



    public ArrayList<String> traerCalentamientoSup(){
        return calentamientoSup;
    }
    public ArrayList<String> traerCalentamientoInf(){
        return calentamientoInf;
    }
    public ArrayList<String> traerCalentamientoMed(){
        return calentamientoMed;
    }


    public void reiniciarBooleanos(){
        editor.putBoolean("BooleanSup",false);
        editor.putBoolean("BooleanInf",false);
        editor.putBoolean("BooleanMed",false);
        editor.putBoolean("BooleanCargo1Vez", false);
        editor.commit();
    }


    public void setearSupTrue(){
        editor.putBoolean("BooleanSup",true);
        editor.commit();
        zonaSuperior.set_finish(true);
    }

    public void setearInfTrue(){
        editor.putBoolean("BooleanInf",true);
        editor.commit();
        zonaInferior.set_finish(true);
    }

    public void setearMedTrue(){
        editor.putBoolean("BooleanMed",true);
        editor.commit();
        zonaMedia.set_finish(true);
    }

    public void setearPrimeraVezTrue(){
        editor.putBoolean("BooleanCargo1Vez", true);
        editor.commit();
    }

    public Boolean traerBooleanSup(){Boolean b = prefs.getBoolean("BooleanSup",false); return b;}
    public Boolean traerBooleanInf(){Boolean b = prefs.getBoolean("BooleanInf",false); return b;}
    public Boolean traerBooleanMed(){Boolean b = prefs.getBoolean("BooleanMed",false); return b;}
    public Boolean traerBooleanPrimeraVez(){Boolean b = prefs.getBoolean("BooleanCargo1Vez",false); return b;}

    public void guardarZona(String z){
        editor.putString("Zona", z);
        editor.commit();
    }

    public String traerZona(){
        String s = prefs.getString("Zona", "");
        return s;
    }

    public void reinciarZona(){

        editor.putString("Zona","");
        editor.commit();
    }



    public void guardarZonaEnMain(){

    }

    public void cargarEventoBD(Date fecha, Boolean tipo )
    {
        Usuario usr=devolverUsuarioActivo();

        // Add a new document with a generated id.
        Map<String, Object> data = new HashMap<>();
        data.put("Fecha", fecha);
        data.put("Tipo", tipo);

        db.collection("usuarios").document(usr.get_idUsuario()).collection("Eventos")
                .add(data)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        // Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                        Log.d("CargarEvento", "Se cargo exitosamente");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("CargarEvento", "Error agregando usuario a BD" + e);

                        // Log.w(TAG, "Error adding document", e);
                    }
                });

    }

    public void traerEventos(){
        db.collection("usuarios").document(usuarioActivo.get_idUsuario()).collection("Eventos")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            Log.d("TraerEvento", "Se cargo exitosamente");
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                tipoEvento event = new tipoEvento();
                                event.setDate(toCalendar(document.getDate("Fecha")));
                                event.setTipo(document.getBoolean("Tipo"));
                                events.add(event);
                            }
                        } else {
                            Log.d("TraerEvento", "F");
                        }
                    }
                });
    }
    public void traerLogros(){
        db.collection("usuarios").document(usuarioActivo.get_idUsuario()).collection("Logros")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            Log.d("TraerEvento", "Se cargo exitosamente");
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                logro log = new logro();
                                log.set_nombres(document.getString("Nombre"));
                                log.set_url(document.get("Imagen").toString());
                                log.set_fecha(toCalendar(document.getDate("Fecha")));
                                log.setunBlocked(document.getBoolean("Unblocked"));
                                arrLogro.add(log);
                            }
                        } else {
                            Log.d("TraerEvento", "F");
                        }
                    }
                });
    }
    public ArrayList<logro> devolverArrayLogro(){return arrLogro;}
    public void cambiarLogros(Date dia, String num) {
        Map<String, Object> data = new HashMap<>();
        data.put("Fecha", dia);
        data.put("Unblocked", true);
        db.collection("usuarios").document(usuarioActivo.get_idUsuario()).collection("Logros")
                .document(num).update(data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Editar2", "DocumentSnapshot successfully updated!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Editar2", "Error updating document", e);
                    }
                });
    }
}