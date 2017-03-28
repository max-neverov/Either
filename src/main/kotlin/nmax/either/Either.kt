package nmax.either

/**
 * @author Maxim Neverov
 */

sealed class Either<T>

data class Left<L> constructor(val v: L) : Either<L>()
data class Right<R> constructor(val v: R) : Either<R>()

sealed class RList<T>
class Nil<T> : RList<T>()
data class Cons<T> constructor(val a: T, val l: RList<T>): RList<T>()

fun main(args: Array<String>) {
    val list = Cons<Int>(1, Cons<Int>(2, (Cons<Int> (3, Nil()))))

    println(foldl(Int::plus, 0, list))
}

fun <A, B> foldl(f: ((B, A) -> B), acc: B, vs: RList<A>): B = when(vs) {
    is Nil -> acc
    is Cons -> foldl(f, f(acc, vs.a), vs.l)
}