def fib(n) {
    if n <= 2 {
        1
    } else {
        n = n - 2
        a = b = 1
        while n > 0 {
            tmp = b
            b = a + b
            a = tmp
            n = n - 1
        }
        b
    }
}
# fib 10



def time_cost_of(f) {
    start = currentTimeMillis()
    f()
    end = currentTimeMillis()
    end - start
}

i = 1
while i < 20 {
    test = () -> {
        # println "fib(" + i + ") = " + fib(i)
    }
    # println "time_cost = " + time_cost_of(test) + "ms"
    
    i = i + 1
}