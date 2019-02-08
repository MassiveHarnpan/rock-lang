def fib1(n) {
    if (n < 2) {
        1;
    } else {
        fib(n - 1) + fib(n - 2);
    }
}

def fib(n) {
    i = 0;
    a = 1;
    b = 1;
    c = a;
    while(i + 1 < n) {
        c = a + b;
        a = b;
        b = c;
        i = i + 1;
    }
    c;
}

num = 50;

while (num >= 0) {
    print("fib[" + num + "] = " + fib(num));
    num = num - 1;
}

lambda_expr = (name) -> {
    print("Hello " + name + " in function " + __function_name__);
};

def function_def(name) {
    print("Hi " + name + " in function " + __function_name__);
}

lambda_expr("Jack");
function_def("Rose");

/* single line comment */
print("after single line comment");
/*
 * multi
 * line
 * comment
 */


(name) -> {print("Hi there " + name);}("Joseph");

print("End");