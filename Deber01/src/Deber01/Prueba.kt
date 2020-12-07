package Deber01

import java.io.File


fun main (){

    val filename: String = "C:/Christian/Universidad/7mo Semestre/Aplicaciones Móviles/Repositorio/AMC-Naula-Lomas-Christian-Alejandro/Deber01/src/Deber01/Directores.txt"

    val lines: List<String>? = File(filename).readLines()
    //lines.forEach { line -> println(line) }
    val ejemplo: String = "hola como estas"

    val aux = lines?.get(0)?.split(", ")
    //println(aux)

    val listaDirectores = lines?.get(0)?.let { tokenizer(it) }
    println(listaDirectores?.get(0))
}


/*fun tokenizador(input: String):ArrayList<String>{

    val result: ArrayList<String> = arrayListOf()
    var inicio: Int = 0
    val fin: Int = input.length
    var i: Int = 0
    var j: Int
    println(fin)
    while (i < fin) {
        if(input.substring(i, if((i+2) > fin) fin else (i+2)).equals(", ")){
            result.add(input.substring(inicio, (i)))
            inicio = (i+2)
            println(result)
        }
        i++
    }
    print(input.substring(30, fin))
    return result
}*/

fun tokenizer(input: String): List<String> {
    val result = input.split(", ")
    return result
}