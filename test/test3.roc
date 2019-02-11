class Greeter {
    prefix = "Hi";

    def __new__() {
        this.prefix = "Hello";
    }

    def of(p) {
        prefix = p;
    }

    def greet(name) {
        print(prefix + " "+ name + "!");
    }
}

g = Greeter();
g.greet("Jack");
g = Greeter.of("Oh ");
g.greet("Rose");
g.toString();

import "E:\\Java Projects\\rock-lang\\test\\test2.roc";
print(fib(10));