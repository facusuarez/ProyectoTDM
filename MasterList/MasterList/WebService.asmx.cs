using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Services;
using System.Data;
using System.Data.SqlClient;
using System.Configuration;
using Newtonsoft.Json;
using System.Web.Script.Services;

namespace MasterList
{
    /// <summary>
    /// Descripción breve de WebService
    /// </summary>
     //[WebService(Namespace = "http://tempuri.org/")]
    [WebService(Namespace = "http://MasterList.somee.com")]
    [WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
    [System.ComponentModel.ToolboxItem(false)]
    // Para permitir que se llame a este servicio web desde un script, usando ASP.NET AJAX, quite la marca de comentario de la línea siguiente. 
    [System.Web.Script.Services.ScriptService]
    public class WebService : System.Web.Services.WebService
    {

        //[WebMethod]
        //public string HelloWorld()
        //{
        //    return "Hello World";
        //}

        [WebMethod]
        [ScriptMethod(ResponseFormat = ResponseFormat.Json)]
        public void getProfesores()
        {
            string sql = "SELECT * FROM Profesores";

            SqlDataAdapter da = new SqlDataAdapter(sql, ConfigurationManager.ConnectionStrings["Connection"].ToString());
            DataSet ds = new DataSet();
            da.Fill(ds);
            Context.Response.Clear();
            Context.Response.ContentType = "application/json";
            Context.Response.Write(JsonConvert.SerializeObject(ds, Newtonsoft.Json.Formatting.Indented));
        }
        [WebMethod]
        public void Insert(string nombre, string apellido, float puntaje)
        {
            string constr = ConfigurationManager.ConnectionStrings["Connection"].ConnectionString;
            using (SqlConnection con = new SqlConnection(constr))
            {
                using (SqlCommand cmd = new SqlCommand("INSERT INTO Profesores (nombre, apellido, puntaje) VALUES (@nombre, @apellido, @puntaje)"))
                {
                    cmd.Parameters.AddWithValue("@nombre", nombre);
                    cmd.Parameters.AddWithValue("@apellido", apellido);
                    cmd.Parameters.AddWithValue("@puntaje", puntaje);
                    cmd.Connection = con;
                    con.Open();
                    cmd.ExecuteNonQuery();
                    con.Close();
                }
            }
        }
         [WebMethod]
        [ScriptMethod(ResponseFormat = ResponseFormat.Json)]
        public string UpdatePuntaje(string id,string puntaje)
        {
            string constr = ConfigurationManager.ConnectionStrings["Connection"].ConnectionString;
            using (SqlConnection con = new SqlConnection(constr))
            {
                using (SqlCommand cmd = new SqlCommand("UPDATE Profesores SET puntaje=@puntaje WHERE id_profesor=@id"))
                {
                    cmd.Parameters.AddWithValue("@id", int.Parse(id));
                    cmd.Parameters.AddWithValue("@puntaje", float.Parse(puntaje));
                    cmd.Connection = con;
                    con.Open();
                    cmd.ExecuteNonQuery();
                    con.Close();
                    return "Se actualizo el profesor id: " + id + " con el puntaje: " + puntaje;
                }
            }
        }
    }
}
