package acmicpc
import java.io.BufferedReader
import java.io.InputStreamReader

const val DIV = 10007
fun main(args: Array<String>) {
    val br = BufferedReader(InputStreamReader(System.`in`))
    val n = br.readLine().toInt()

    val arr = IntArray(n + 1)

    arr[1] = 1
    if (n == 1) {
        println(1)
        return
    }

    arr[2] = 2

    for (i in 3..n) {
        arr[i] = (arr[i - 1] + arr[i - 2]) % DIV
    }

    println(arr[n])
}
