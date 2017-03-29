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
    println(foldl(Int::plus, 0, toRList(listOf(1,2,3,4,5))))
}

// foldl :: (a -> b -> a) -> a -> [b] -> a
fun <A, B> foldl(f: ((A, B) -> A), acc: A, xs: RList<B>): A = when(xs) {
    is Nil -> acc
    is Cons -> foldl(f, f(acc, xs.a), xs.l)
}

// fromList :: List a -> [a]
// fromList Nil = []
// fromList (Cons x xs) = x : fromList xs
// cannot use inlined fun with reifined type because of recursion, hence list as a result instead of an array
fun <T> fromRList(xs: RList<T>) : List<T> = when (xs) {
    is Nil -> emptyList<T>()
    is Cons -> fromRList(xs.l).plus(xs.a)
}

// toList :: [a] -> List a
// toList [] = Nil
// toList (x:xs) = Cons x (toList xs)
fun <T> toRList(l: List<T>) : RList<T> {
    when {
        l.isEmpty() -> return Nil<T>()
        else -> return Cons<T> (l.get(0), toRList(l.subList(1, l.size)))
    }
}