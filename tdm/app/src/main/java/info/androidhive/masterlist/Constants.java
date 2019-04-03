package info.androidhive.masterlist;

public class Constants {

    private static final String URL = "localhost:8081/";
//http://www.masterlist.somee.com/WebService.asmx/
    public static final String DB_NAME = "dbMasterList";
    public final static String URL_UPDATE_PUNTAJE = URL + "UpdatePuntaje";
    public final static String URL_GET_AVG_COMENTARIO = URL + "getAVGComentarios?idProfe=";
    public static final String URL_GET_PROFESORES= URL + "getProfesores";
    public final static String URL_GET_COMENTARIO = URL + "getComentarios?idProfe=";
    public final static String URL_GET_MATERIAS_PROFE = URL + "getProfeCatedraComision?idProfe=";
    public final static String URL_INSERT_COMENTARIO = URL + "InsertComentario";
    public final static String URL_CREATE_USER = URL + "users/createUserInfo";

    public static final String ACCOUNT_TYPE = "com.lm.example.accountmanagersample";
    public static final String AUTH_TOKEN_TYPE="ROOT";
    public static final String KEY_ACCOUNT="KEY_ACCOUNT";
    public static int ACCOUNT_PERMISSION_CODE =0;

}
