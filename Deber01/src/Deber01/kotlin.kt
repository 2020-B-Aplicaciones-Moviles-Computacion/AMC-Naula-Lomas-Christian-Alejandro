import java.io.File
import java.util.*

fun main() {
    val fileNameDirectores: String =
        "C:/Christian/Universidad/7mo_Semestre/Aplicaciones_Moviles/Repositorio/AMC-Naula-Lomas-Christian-Alejandro/Deber01/src/Deber01/Directores.txt"
    val fileNamePeliculas: String =
        "C:/Christian/Universidad/7mo_Semestre/Aplicaciones_Moviles/Repositorio/AMC-Naula-Lomas-Christian-Alejandro/Deber01/src/Deber01/Peliculas.txt"

    do {
        println("\n-----------------------------------------------------------------------------")
        println("\nElija la acción que desea realizar ingresando el NÚMERO DE LA OPCIÓN")
        println("\nDIRECTORES")
        println(
            "1 -> Mostrar Directores\n" +
                    "2 -> Agregar Director\n" +
                    "3 -> Actualizar Director\n" +
                    "4 -> Eliminar Director"
        )
        println("\nPELÍCULAS")
        println(
            "5 -> Mostrar Todas las Películas\n" +
                    "6 -> Mostrar Películas por Director\n" +
                    "7 -> Agregar Película\n" +
                    "8 -> Actualizar Película\n" +
                    "9 -> Eliminar Película\n\n" +
                    "0 -> SALIR\n"
        )
        println("-----------------------------------------------------------------------------\n")

        val scan = Scanner(System.`in`)
        var n = scan.nextLine().trim().toInt()


        when (n) {
            1 -> {
                desplegarDirectores(fileNameDirectores)//desplegar()
            }

            2 -> {
                println(
                    "Ingrese los datos del nuevo Director con el siguiente formato\n" +
                            "'Nombre', 'Apellido', 'Nacionalidad', 'Edad', 'Correo Electronico'\n " +
                            "ó 0 para CANCELAR")

                val scan1 = Scanner(System.`in`)
                val n1 = scan.nextLine()
                if (!n1.equals("0")) {
                    //println(n1)
                    insertarDirector(n1, fileNameDirectores)
                    println("Director ingresado correctamente\n")
                }
            }

            3 -> {
                println("\nDigite el número del Director a actualizar")
                desplegarDirectores(fileNameDirectores)
                println("0, CANCELAR")
                val scan = Scanner(System.`in`)
                val n = scan.nextLine()
                if (!n.equals("0")) {
                    val director: String = busquedaDirectoresbyId(fileNameDirectores, n)
                    val idDirector = tokenizer(director)
                    if (!director.equals("vacio")) {
                        print("${director}\n")
                        println(
                            "Ingrese los datos modificados con el mismo formato\n" +
                                    "'nombre', 'apellido', 'nacionalidad', 'edad', 'correo electronico'\n" +
                                    "ó 0 para CANCELAR")
                        val scan1 = Scanner(System.`in`)
                        val n1 = scan1.nextLine()
                        if (!n1.equals("0")) {
                            actualizarDirector(n1, fileNameDirectores, idDirector[0])
                            println("Director actualizado correctamente\n")
                        }
                    } else println("No existe un director asociado con ese número\n")
                }
            }

            4 -> {
                println("\nDigite el número del Director a eliminar")
                desplegarDirectores(fileNameDirectores)
                val scan = Scanner(System.`in`)
                val n = scan.nextLine()
                val director: String = busquedaDirectoresbyId(fileNameDirectores, n)
                if (!director.equals("vacio")) {
                    print("${director}\n")
                    println(
                        "Está seguro que desea eliminar este director\n" +
                                "Digite (Y) para confirmar o (N) para cancelar"
                    )
                    val scan1 = Scanner(System.`in`)
                    val n1 = scan1.nextLine()
                    if (n1.toLowerCase().equals("y")) {
                        eliminarDirector(director, fileNameDirectores)
                        println("Director eliminado correctamente\n")
                    } else{
                        println("Operación Cancelada\n")
                    }
                    //actualizarDirector(n1, fileNameDirectores, idDirector[0])
                } else println("No existe un director asociado con ese número\n")
            }

            5 -> {
                desplegarPeliculas(fileNamePeliculas, fileNameDirectores)
            }

            6 -> {
                println("\nDigite el número del Director.")
                desplegarDirectores(fileNameDirectores)
                println("0, CANCELAR")
                val scan = Scanner(System.`in`)
                val n = scan.nextLine()
                if (!n.equals("0")) {
                    desplegarPeliculasbyDirector(fileNamePeliculas, fileNameDirectores, n)
                }
            }

            7 -> {
                println("\nDigite el número del Director de la nueva película.")
                desplegarDirectores(fileNameDirectores)
                println("0, CANCELAR")
                val scan = Scanner(System.`in`)
                val n = scan.nextLine()
                if (!n.equals("0")) {
                    println(
                        "Ingrese la nueva película en el siguiente formato\n" +
                                "'Nombre', 'Género', 'Duración', 'Año'\n" +
                                "ó 0 para CANCELAR"
                    )
                    val n1 = scan.nextLine()
                    if (!n1.equals("0")) {
                        insertarPelicula(n1, fileNamePeliculas, n)
                        println("Película ingresado correctamente\n")
                    }


                }

            }

            8 -> {
                println("\nDigite el número de la película a actualizar.")
                desplegarPeliculas(fileNamePeliculas, fileNameDirectores)
                println("0, CANCELAR")
                val scan = Scanner(System.`in`)
                val n = scan.nextLine()
                if (!n.equals("0")) {
                    println("La pelícual seleccionada es: ")

                    desplegarPeliculabyId(fileNamePeliculas, fileNameDirectores, n)
                    println(
                        "Ingrese los datos modificados con el mismo formato\n" +
                                "'Nombre', 'Género', 'Duración', 'Año'\n " +
                                "ó 0 para CANCELAR"
                    )
                    val n1 = scan.nextLine()
                    if (!n1.equals("0")) {
                        println("Seleccione el director de la película")
                        desplegarDirectores(fileNameDirectores)
                        println("0, CANCELAR")
                        val n2 = scan.nextLine()
                        if (!n2.equals("0")) {
                            actualizarPelicula(n1, fileNamePeliculas, n, n2)
                            println("Película actualizada correctamente\n")
                        }
                    }
                }
            }

            9 -> {
                println("\nDigite el número de la pelícuña a eliminar")
                desplegarPeliculas(fileNamePeliculas, fileNameDirectores)
                println("0, CANCELAR")
                val scan = Scanner(System.`in`)
                val n = scan.nextLine()
                if (!n.equals("0")) {
                    println("La pelícual seleccionada es: ")
                    var pelicula = busquedPeliculabyId(fileNamePeliculas, n)
                    desplegarPeliculabyId(fileNamePeliculas, fileNameDirectores, n)
                    println(
                        "Está seguro que desea eliminar este director\n" +
                                "Digite (Y) para confirmar o (N) para cancelar"
                    )
                    val scan1 = Scanner(System.`in`)
                    val n1 = scan1.nextLine()
                    if (n1.toLowerCase().equals("y")) {
                        eliminarPelicula(pelicula, fileNamePeliculas)
                        println("Película eliminada correctamente\n")
                    }
                }
                else{
                    println("Operación Cancelada\n")
                }
            }

            else -> {
                n =  0
            }

        }


    } while (n != 0)

}


// Fucnion para tokenizar cada línea leida
fun tokenizer(input: String): List<String> {
    val result = input.split(", ")
    return result
}


fun desplegarDirectores(filename: String) {
    //Leemos los archivos línea por línea
    println("Número, Nombre, Apellido, Nacionalidad, Edad, Correo")
    val lines: List<String> = File(filename).readLines()
    var i: Int = 0
    while (i < lines.size) {
        var director = tokenizer(lines[i])
        var directorAux = Director(
            director[0].toInt(), director[1], director[2], director[3],
            director[4].toInt(), director[5]
        ).toString()
        println(directorAux)
        i++
    }
}


// Devolvemos el elemento igual al id
fun busquedaDirectoresbyId(filename: String, id: String): String {
    val lines: List<String> = File(filename).readLines()
    var i: Int = 0
    while (i < lines.size) {
        var director = tokenizer(lines[i])
        if (director[0].equals(id)) {
            return lines.get(i)
        }
        i++
    }
    return "vacio"
}


// Ingresamos un nuevo director
fun insertarDirector(input: String, filename: String) {
    var director = tokenizer(input)
    var id: Int = consultarIdDirector(filename)
    var nuevodirector = Director(
        id, director[0], director[1],
        director[2], director[3].toInt(), director[4],
    ).ingresar(filename)
}


fun actualizarDirector(input: String, filename: String, id: String) {
    var director = tokenizer(input)
    var nuevodirector = Director(
        id.toInt(), director[0], director[1],
        director[2], director[3].toInt(), director[4],
    ).actualizar(filename)
}


fun eliminarDirector(input: String, filename: String) {
    var director = tokenizer(input)
    var directorEliminar = Director(
        director[0].toInt(), director[1], director[2],
        director[3], director[4].toInt(), director[5]
    ).eliminarDirector(filename)
}


// Consultamos el id correspondiente
fun consultarIdDirector(filename: String): Int {
    val lines: List<String> = File(filename).readLines()
    var idDirector = tokenizer(lines[(lines.size - 1)])
    return (idDirector[0].toInt() + 1)
}


fun desplegarPeliculas(filenamePeliculas: String, filenameDirectores: String) {
    println("Número, Nombre, Género, Duración, Año, Director")
    val lines: List<String> = File(filenamePeliculas).readLines()
    var i: Int = 0
    while (i < lines.size) {
        var pelicula = tokenizer(lines[i])
        var peliculaAux = Pelicula(
            pelicula[0].toInt(), pelicula[1], pelicula[2], pelicula[3].toInt(),
            pelicula[4].toInt(), pelicula[5].toInt()
        )
        var director = tokenizer(busquedaDirectoresbyId(filenameDirectores, pelicula[5]))
        println(peliculaAux.toString() + "${director[1]} ${director[2]}")
        i++
    }
}

fun desplegarPeliculasbyDirector(filenamePeliculas: String, filenameDirectores: String, idDirector: String){
    println("Número, Nombre, Género, Duración, Año, Director")
    val lines: List<String> = File(filenamePeliculas).readLines()
    var i: Int = 0
    while (i < lines.size) {
        var pelicula = tokenizer(lines[i])
        if (pelicula[5].equals(idDirector)) {
            var peliculaAux = Pelicula(
                pelicula[0].toInt(), pelicula[1], pelicula[2], pelicula[3].toInt(),
                pelicula[4].toInt(), pelicula[5].toInt()
            )
            var director = tokenizer(busquedaDirectoresbyId(filenameDirectores, pelicula[5]))
            println(peliculaAux.toString() + "${director[1]} ${director[2]}")
        }
        i++
    }
}


fun insertarPelicula(input: String, filename: String, idDirecor: String) {
    var pelicula = tokenizer(input)
    var id: Int = consultarIdPelícula(filename)
    var nuevapelicula = Pelicula(
        id, pelicula[0], pelicula[1],
        pelicula[2].toInt(), pelicula[3].toInt(), idDirecor.toInt()
    ).ingresar(filename)
}


fun actualizarPelicula(input: String, filename: String, id: String, idDirector: String) {
    var pelicula = tokenizer(input)
    var nuevaPelicula = Pelicula(
        id.toInt(), pelicula[0], pelicula[1],
        pelicula[2].toInt(), pelicula[3].toInt(), idDirector.toInt()
    ).actualizar(filename)

}

fun eliminarPelicula(input: String, filename: String) {
    var pelicula = tokenizer(input)
    var peliculaEliminar = Pelicula(
        pelicula[0].toInt(), pelicula[1], pelicula[2],
        pelicula[3].toInt(), pelicula[4].toInt(), pelicula[5].toInt()
    ).eliminarPelicula(filename)
}

fun busquedPeliculabyId(filename: String, id: String): String {
    val lines: List<String> = File(filename).readLines()
    var i: Int = 0
    while (i < lines.size) {
        var pelicula = tokenizer(lines[i])
        if (pelicula[0].equals(id)) {
            return lines.get(i)
        }
        i++
    }
    return "vacio"
}

fun desplegarPeliculabyId(fileNamePeliculas: String, fileNameDirectores: String, id: String) {
    var pelicula = tokenizer(busquedPeliculabyId(fileNamePeliculas, id))
    var peliculaAux = Pelicula(
        pelicula[0].toInt(), pelicula[1], pelicula[2], pelicula[3].toInt(),
        pelicula[4].toInt(), pelicula[5].toInt()
    )
    var director = tokenizer(busquedaDirectoresbyId(fileNameDirectores, pelicula[5]))
    println(peliculaAux.toString() + "${director[1]} ${director[2]}")
}


// Consultamos el id correspondiente
fun consultarIdPelícula(filename: String): Int {
    val lines: List<String> = File(filename).readLines()
    var i: Int = 0
    var idPelícula = tokenizer(lines[(lines.size - 1)])
    return (idPelícula[0].toInt() + 1)
}


















