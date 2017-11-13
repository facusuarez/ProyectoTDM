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
        [ScriptMethod(ResponseFormat = ResponseFormat.Json)]
        public void getComentarios(int idProfe)
        {
            string sql = "SELECT * FROM comentario WHERE id_profesor="+idProfe;

            SqlDataAdapter da = new SqlDataAdapter(sql, ConfigurationManager.ConnectionStrings["Connection"].ToString());
            DataSet ds = new DataSet();
            da.Fill(ds);
            Context.Response.Clear();
            Context.Response.ContentType = "application/json";
            Context.Response.Write(JsonConvert.SerializeObject(ds, Newtonsoft.Json.Formatting.Indented));
        }

        [WebMethod]
        [ScriptMethod(ResponseFormat = ResponseFormat.Json)]
        public void getProfeCatedraComision(string idProfe)
        {
            string sql = "SELECT profesores.*, comisiones.nombre as comision, catedras.nombre  as catedra, catedras.alias  FROM profe_catedra_comision INNER JOIN profesores ON profe_catedra_comision.id_profesor = profesores.id_profesor INNER JOIN comisiones ON comisiones.id_comision = profe_catedra_comision.id_comision  INNER JOIN catedras ON catedras.id_catedra = profe_catedra_comision.id_catedra WHERE profesores.id_profesor=" + idProfe;

            SqlDataAdapter da = new SqlDataAdapter(sql, ConfigurationManager.ConnectionStrings["Connection"].ToString());
            DataSet ds = new DataSet();
            da.Fill(ds);
            Context.Response.Clear();
            Context.Response.ContentType = "application/json";
            Context.Response.Write(JsonConvert.SerializeObject(ds, Newtonsoft.Json.Formatting.Indented));
        }

        [WebMethod]
        [ScriptMethod(ResponseFormat = ResponseFormat.Json)]
        public void InsertProfesor(string nombre, string apellido, float puntaje)
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
        public void InsertComentario(int id_profesor, int id_usuario, string descripcion, float puntos)
        {
            string constr = ConfigurationManager.ConnectionStrings["Connection"].ConnectionString;
            using (SqlConnection con = new SqlConnection(constr))
            {
                using (SqlCommand cmd = new SqlCommand("INSERT INTO comentario (id_profesor, id_usuario, descripcion, puntos, fecha_hora) VALUES (@id_profesor, @id_usuario, @descripcion, @puntos, @fecha_hora)"))
                {
                    cmd.Parameters.AddWithValue("@id_usuario", id_usuario);
                    cmd.Parameters.AddWithValue("@id_profesor", id_profesor);
                    cmd.Parameters.AddWithValue("@puntos", puntos);
                    cmd.Parameters.AddWithValue("@fecha_hora", DateTime.Now.ToString());
                    cmd.Parameters.AddWithValue("@descripcion", descripcion);
                    cmd.Connection = con;
                    con.Open();
                    cmd.ExecuteNonQuery();
                    con.Close();
                    Context.Response.Clear();
                    Context.Response.ContentType = "application/json";
                    Context.Response.Write("Se agrego el comentario: '" + descripcion+"' con el puntaje: "+puntos);
                }
            }
        }
        [WebMethod]
        [ScriptMethod(ResponseFormat = ResponseFormat.Json)]
        public void UpdatePuntaje(string id,string puntaje)
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
                    Context.Response.Clear();
                    Context.Response.ContentType = "application/json";
                    Context.Response.Write("Se actualizo el profesor id: " + id + " con el puntaje: " + puntaje); 
                }
            }
        }
    }
}
