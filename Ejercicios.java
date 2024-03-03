import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

public class Ejercicios{

    public static void main(String[] args) {
        // Ejercicio 13
        List<Double> numeros = List.of(1.0, 2.0, 3.0, 4.0, 5.0);
        double suma = integral(0, numeros.size(), 1, x -> numeros.get((int) x));
        System.out.println("La suma de los elementos de la lista es: " + suma);

        // Ejercicio 14
        IntStream.of(1, 2, 3, 4, 5).forEach(System.out::println);
        IntStream.range(1, 6).forEach(System.out::println);
        IntStream.iterate(1, num -> num + 1).limit(5).forEach(System.out::println);
        new Random().doubles(5).forEach(System.out::println);

        // Ejercicio 15
        System.out.println("Suma de 0 a 5: " + suma(0, 5));
        System.out.println("Factorial de 5: " + factorial(5));
        System.out.println("Potencia de 2^3: " + potencia(2, 3));
        System.out.println("Media de la lista: " + media(numeros));
        System.out.println("Desviación estándar de la lista: " + desviacionEstandar(numeros));
        System.out.println("Suma de los primeros números pares hasta 9: " + sumaPares(9));
        System.out.println("Suma de los elementos pares de la lista: " + sumaParesLista(numeros));
        System.out.println("Lista de números pares de [1, 2, 6, 11]: " + obtenerListaPares(List.of(1, 2, 6, 11)));
        System.out.println("Lista de los primeros números pares hasta 9: " + listaPares(9));
        System.out.println("Producto escalar de [1, 2, 3] y [2, 4, 6]: " + productoEscalar(List.of(1, 2, 3), List.of(2, 4, 6)));
        System.out.println("Elemento 7 de Fibonacci: " + fibonacci(7));

        // Ejercicio 16
        double cociente = cocienteFibonacci(13, 12);
        double comparacion = 1 + Math.sqrt(5) / 2;
        System.out.println("Cociente: " + cociente);
        System.out.println("Comparación con 1+√5/2: " + comparacion);

        // Ejercicio 17
        Personas personas = new Personas();
        personas.annadirPersona(new Persona("Juan", LocalDate.of(1990, 5, 15)));
        personas.annadirPersona(new Persona("Maria", LocalDate.of(1985, 10, 30)));
        personas.annadirPersona(new Persona("Pedro", LocalDate.of(2000, 3, 20)));
        System.out.println("Persona más joven: " + personas.elMasJoven());
        System.out.println("Suma de las edades: " + personas.calcularSumaEdades());
        System.out.println("Edad mínima: " + personas.calcularEdadMinima());
        System.out.println("Media de edades: " + personas.calcularMediaDeEdad());
    }

    // Ejercicio 13
    public static double integral(double a, double b, double h, Function<Double, Double> funcion) {
        double suma = 0;
        for (double x = a; x < b; x += h) {
            suma += funcion.apply(x);
        }
        return suma;
    }

    // Ejercicio 15
    public static long suma(int a, int b) {
        return IntStream.rangeClosed(a, b).sum();
    }

    public static long factorial(int n) {
        return IntStream.rangeClosed(1, n).reduce(1, (x, y) -> x * y);
    }

    public static double potencia(double base, double exponente) {
        return Math.pow(base, exponente);
    }

    public static double media(List<Double> numeros) {
        return numeros.stream().mapToDouble(Double::doubleValue).average().orElse(0);
    }

    public static double desviacionEstandar(List<Double> numeros) {
        double media = media(numeros);
        return Math.sqrt(numeros.stream().mapToDouble(x -> Math.pow(x - media, 2)).sum() / numeros.size());
    }

    public static int sumaPares(int n) {
        return IntStream.rangeClosed(2, n).filter(x -> x % 2 == 0).sum();
    }

    public static int sumaParesLista(List<Double> numeros) {
        return numeros.stream().mapToInt(Double::intValue).filter(x -> x % 2 == 0).sum();
    }

    public static List<Integer> obtenerListaPares(List<Integer> numeros) {
        return numeros.stream().filter(x -> x % 2 == 0).collect(Collectors.toList());
    }

    public static List<Integer> listaPares(int n) {
        return IntStream.iterate(n, i -> i - 1).limit(n / 2).map(x -> x * 2).boxed().collect(Collectors.toList());
    }

    public static int productoEscalar(List<Integer> lista1, List<Integer> lista2) {
        return IntStream.range(0, lista1.size()).map(i -> lista1.get(i) * lista2.get(i)).sum();
    }

    /**
     * @param n
     * @return
     */
    public static int fibonacci(int n) {
        return IntStream.iterate(new int[]{0, 1}, operand -> operand.extracted()).limit(n).mapToInt(t -> t[0]).sum();
    }

    private static int[] extracted(int t) {
        return new int[]{t[1], t[0] + t[1]};
    }

    // Ejercicio 16
    public static double cocienteFibonacci(int num1, int num2) {
        return (double) fibonacci(num1) / fibonacci(num2);
    }

}

class Persona {
    private String nombre;
    private LocalDate fechaDeNacimiento;

    public Persona(String nombre, LocalDate fechaDeNacimiento) {
        this.nombre = nombre;
        this.fechaDeNacimiento = fechaDeNacimiento;
    }

    public long calcularEdad() {
        return LocalDate.now().getYear() - fechaDeNacimiento.getYear();
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public String toString() {
        return "Persona{" +
                "nombre='" + nombre + '\'' +
                ", fechaDeNacimiento=" + fechaDeNacimiento +
                '}';
    }
}

class Personas {
    private List<Persona> listaPersonas;

    public void annadirPersona(Persona persona) {
        listaPersonas.add(persona);
    }

    public Persona elMasJoven() {
        return listaPersonas.stream().min((p1, p2) -> p1.getFechaDeNacimiento().compareTo(p2.getFechaDeNacimiento())).orElse(null);
    }

    public long calcularSumaEdades() {
        return listaPersonas.stream().mapToLong(Persona::calcularEdad).sum();
    }

    public long calcularEdadMinima() {
        return listaPersonas.stream().mapToLong(Persona::calcularEdad).min().orElse(0);
    }

    public double calcularMediaDeEdad() {
        return listaPersonas.stream().mapToLong(Persona::calcularEdad).average().orElse(0);
    }
}
