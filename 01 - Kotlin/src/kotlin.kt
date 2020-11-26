import java.util.*
import kotlin.collections.ArrayList

fun main() {
    //println("Hola mundo")

    var edadProfesor = 31
    //var edadProfesor: Int = 31
    var sueldoProfesor = 31.23
    //var sueldoProfesor: Double = 31.23
    //Duck typing

    //VARIABLES MUTABLES (reaccionando valores)
    var edadcachorro: Int
    edadcachorro = 1
    edadcachorro = 2
    edadcachorro = 10
    edadcachorro = 75


    //VARIABLES INMUTABLES
    val numerocedula = 1724723679


    //Tipos de Variables
    val nombreProfesor: String = "Adian Eguez"
    val sueldo: Double = 400.0
    val estadoCivil: Char = 'S'
    val fechaNacimiento = Date()


    //Condicionales
    if (sueldo == 20.2) {

    } else {

    }

    when (sueldo) {
        12.2 -> {
            //println("Sueldo normal")
        }
        -12.2 -> {
            //println("Sueldo negativo")
        }
        else -> {
            //println("sueldo no reconocido")
        }
    }


    val sueldoMayorAlEstablecido: Boolean = if (sueldo == 12.2) false else true
    //condicion ? bloque-true : bloque-false


    //FUNCIONES
    imprimirNombre("Adrian")

    //calcularSueldo(1000.00)
    calcularSueldo(1000.00)
    //calcularSueldo(1000.00,14.00)
    calcularSueldo(1000.00, 14.00)
    //calcularSueldo(1000.00,14.00,3)
    calcularSueldo(1000.00, 14.00, 3)

    //mandar tasa que se tiene por defecto, manda sueldo y calculo especial unicamente
    calcularSueldo(
        1000.00,
        //14.00,
        calculoEspecial = 3 // Named Parameters (parametros nombrados)
    )


    //ARREGLOS
    //  Arreglos inmutables, estaticos (tamaño definido, no se pueden añadir elementos)
    val arregloInmutable: Array<Int> = arrayOf(1, 2, 3)

    //  Arreglos mutables, dinamico (tamaño que puede variarse, pueden incrementarse elementos)
    val arregloMutable: ArrayList<Int> = arrayListOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)

// ----------------------------------------------------------------------------------------------------

    // OPERADORES - sirven para arreglso inmutables y mutables

    //arregloInmutable.forEach {  }
    //arregloMutable.forEach {  }

    // Unit - no devuelve nada en Kotlin, como variables void en Java
    val respuestaforeach: Unit = arregloInmutable.forEach { valorIteracion -> //sin definir tipo de dato duck typing
        //valorIteracion: Int ->
        //println("Valor: ${valorIteracion}")
    }

    // operador foreachindexed

    val respuestaforeachIndexed: Unit = arregloInmutable.forEachIndexed { indice, valorIteracion -> //sin definir tipo de dato duck typing
        //valorIteracion: Int ->
        //println("Valor: ${valorIteracion}, Indice: ${indice}")
    }


    // Map -> muta el arreglo
    //1. enviamos el nuevo valor de la iteracion
    //2. nos devuelve es un NUEVO ARRREGLO con valores modificados
    // sintaxis con mas lineas de codigo
    val respuestaMap: List<Int> = arregloMutable.map { valorActualIteracion ->
        return@map valorActualIteracion * 10
    }
    //println(respuestaMap)

    // sintaxis corta
    arregloMutable.map { valorActualIteracion -> valorActualIteracion * 30 }


    // Filter -> Filtrar el arreglo
    // 1. DEvolver una expresion (true o false)
    // 2. Nuevo arreglo filtrado
    // sintaxis con mas lineas de codigo
    val respuestaFilter: List<Int> = arregloMutable.filter { valorActualIteracion ->
        val mayorACinco: Boolean = valorActualIteracion > 5
        return@filter mayorACinco
    }
    //println(respuestaFilter)

    // sintaxis corta
    arregloMutable.filter { i -> i > 5 }

    arregloMutable.filter { it > 5 }


    //---------------------------------------------CLASE 25/11/2020------------------------------------

    //Operador ANY ALL -> Revisar una condicion dentro del arreglo
    // OR - AND
    // OR = ANY
    // OR (FALSO - todos tienen q ser falsos)
    // OR (VERDADERO - si uno es verdadero todos son verdadero)
    // AND = ALL
    // AND (FALSO - si uno es falso todos son falsos)
    // AND (VERDAVERO - todos tienen q ser verdadero)

    val respuestaAny = arregloMutable.any { valoractualany ->
        return@any valoractualany > 5 // true or false
    }
    /*println(arregloMutable)
    println(respuestaAny)*/

    val respuestaAll = arregloMutable.all { valoractualall ->
        return@all valoractualall > 5 // true or false
    }
    /*println(arregloMutable)
    println(respuestaAll)*/


    //------------------------------------------------------
    // Operador REDUCE
    // 1. devuelve el acumulado
    // 2. en que valor empieza
    // [1,2,3,4,5]
    /*
    * 0 = 0 + 1
    * 1 = 1 + 2
    * 3 = 3 + 3
    * 6 = 6 + 4
    * 10 = 10 + 5
    * resut = 15
    */

    val respuestaReduce = arregloMutable.reduce { //valor acumulado por defecto es cero
            acumulado, valoractualIteracion ->
        return@reduce acumulado + valoractualIteracion
    }

    /*println(arregloMutable)
    println(respuestaReduce)*/

    // fold - especie de reduce
    val respuestaFold = arregloMutable.fold(
        100,
        {
                acumulado, valorActualIteracion ->
            return@fold valorActualIteracion
        }
    )

    /*println(arregloMutable)
    println(respuestaFold)*/


    //arregloMutable.fold() - empieza desde el principio
    //arregloMutable.foldRight() - empieza desde el final

    //arregloMutable.reduce() - empieza desde el principio
    //arregloMutable.reduceRight() - empieza desde el final



    // CONCATENACION

    val vidaActual = arregloMutable
        .map { it * 2.3 }
        .filter { it > 20 }
        .fold(100.80 , {acc, i -> acc - i})
    //.also { println(it) }
    //println("Valor vida actual ${vidaActual}")


    val ejemploUno = Suma(1,2,3)
    val ejemploDos = Suma(1,null,3)
    val ejemploTres = Suma(null,null,null)
    println(ejemploUno.sumar())
    println(Suma.historialSumas)
    println(ejemploDos.sumar())
    println(Suma.historialSumas)
    println(ejemploTres.sumar())
    println(Suma.historialSumas)













} // main cerrado






fun imprimirNombre(nombre: String) {
    //println("Nombre: ${nombre}")
}


fun calcularSueldo(sueldo: Double,
                   tasa: Double = 12.00, // Opcional con valor por defecto de 12.00
                   calculoEspecial: Int? = null // calculo especial es un entero con valor incial de nulo
): Double {
    //String -> String?   puede ser nulo
    //int -> int?   puede ser nulo
    //date -> date?   puede ser nulo

    if (calculoEspecial == null) {
        return sueldo * (100.00 / tasa)
    } else {
        return sueldo * (100.00 / tasa) * calculoEspecial
    }
}



// CLASES
// abstracta

abstract class NumerosJAva{
    protected val numeroUno:Int
    private val numeroDos: Int

    constructor(uno: Int, dos:Int){ // bloque de codigo del constructor primario
        numeroUno = uno
        numeroDos = dos
        println(numeroUno)
    }
}

// forma de escribir clases estilo Kotlin
abstract class Numeros (    // constructor primario
    protected var numeroUno: Int,
    protected var numeroDos: Int){

    init { //bloque de codigo del constructor primario
        println(numeroUno)
    }
}



// Clase

class Suma(
    uno: Int,   // si no ponemos protected, public, val , var -> no son parte de la clase
    // son solo parametros
    dos: Int,
    protected var tres: Int

): Numeros(uno, dos){   // extender (heredar) una clase abstracta (inicializar clases super)


    init { // llamada al constructor primario
        this.numeroUno  // tenemos acceso por estar en el constructor primario
        this.numeroDos
        this.sumar()
        println("Construcotr primario init")
    }

    //segundo constructor

    constructor(
        uno:Int,
        dos:Int?,
        tres: Int
    ) : this(
        uno,
        if(dos == null) 0 else dos,
        tres
    ) {
    }

    // tercer constructor
    constructor(
        uno:Int?,
        dos:Int?,
        tres: Int?
    ) : this(
        if(uno == null) 0 else uno,
        if(dos == null) 0 else dos,
        if(tres == null) 0 else tres
    ) {
    }

    public fun sumar():Int{
        //this.uno // no existen
        //this.dos // no existen
        this.tres
        val total: Int = this.numeroUno + this.numeroDos
        Suma.agragerHistorial(total)
        return total
    }


    companion object{  //singleton // metodos y atributos
        val historialSumas = arrayListOf<Int>()
        fun agragerHistorial(nuevaSuma:Int){
            this.historialSumas.add(nuevaSuma)
        }
    }

}


class BaseDeDatos(){
    companion object{
        val datos = arrayListOf<Int>()
    }
}













