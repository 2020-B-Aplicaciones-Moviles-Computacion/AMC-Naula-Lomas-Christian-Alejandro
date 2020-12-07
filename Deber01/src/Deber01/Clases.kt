import java.io.File
import java.io.PrintWriter

class Director(var id: Int, var nombre: String, var apellido: String, var nacionalidad: String,
               var edad: Int, var correo: String) {

    override fun toString(): String {
        return "${id}, ${nombre}, ${apellido}, ${nacionalidad}, ${edad}, ${correo})"
    }

    fun ingresar(filename: String){
        val text = File(filename)
        File(filename).appendText("\n${id}, ${nombre}, ${apellido}" +
                ", ${nacionalidad}, ${edad}, ${correo}")
    }

    fun actualizar (filename: String){
        var text: MutableList<String> = File(filename).readLines().toMutableList()

        var i: Int = 0
        while (i < text.size){
            var aux = tokenizer(text[i])
            if (aux[0].equals(id.toString())){
                text[i] = "${id}, ${nombre}, ${apellido}" +
                        ", ${nacionalidad}, ${edad}, ${correo}"
                break
            }
            i++
        }
        val writer = PrintWriter(filename)
        writer.print("")
        writer.close()
        i = 0
        while (i < text.size){
            if (i != 0) File(filename).appendText("\n${text[i]}")
            else File(filename).appendText("${text[i]}")
            i++
        }
    }


    fun eliminarDirector( filename: String){
        var text: MutableList<String> = File(filename).readLines().toMutableList()
        var i: Int = 0
        while (i < text.size){
            var aux = tokenizer(text[i])
            if (aux[0].equals(id.toString())){
                text[i] = "vacio"
                break
            }
            i++
        }
        val writer = PrintWriter(filename)
        writer.print("")
        writer.close()
        i = 0
        while (i < text.size){
            if (!text[i].equals("vacio")) {
                if (i != 0) File(filename).appendText("\n${text[i]}")
                else File(filename).appendText("${text[i]}")
            }
            i++
        }
        println(text)
    }


}




class Pelicula (var id: Int, var nombre: String, var genero: String, var duracion: Int,
                var año: Int, var director: Int){

    override fun toString(): String {
        return "${id}, ${nombre}, ${genero}, ${duracion}, ${año}, "
    }

    fun ingresar(filename: String){
        val text = File(filename)
        File(filename).appendText("\n${id}, ${nombre}, ${genero}" +
                ", ${duracion}, ${año}, ${director}")
    }

    fun actualizar (filename: String){
        var text: MutableList<String> = File(filename).readLines().toMutableList()

        var i: Int = 0
        while (i < text.size){
            var aux = tokenizer(text[i])
            if (aux[0].equals(id.toString())){
                text[i] = "${id}, ${nombre}, ${genero}" +
                        ", ${duracion}, ${año}, ${director}"
                break
            }
            i++
        }
        val writer = PrintWriter(filename)
        writer.print("")
        writer.close()
        i = 0
        while (i < text.size){
            if (i != 0) File(filename).appendText("\n${text[i]}")
            else File(filename).appendText("${text[i]}")
            i++
        }
    }

    fun eliminarPelicula(filename: String){
        var text: MutableList<String> = File(filename).readLines().toMutableList()
        var i: Int = 0
        while (i < text.size){
            var aux = tokenizer(text[i])
            if (aux[0].equals(this.id.toString())){
                text[i] = "vacio"
                break
            }
            i++
        }
        val writer = PrintWriter(filename)
        writer.print("")
        writer.close()
        i = 0
        while (i < text.size){
            if (!text[i].equals("vacio")) {
                if (i != 0) File(filename).appendText("\n${text[i]}")
                else File(filename).appendText("${text[i]}")
            }
            i++
        }
        println(text)
    }

}