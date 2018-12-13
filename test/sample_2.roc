#show_log false
class Greeter{
    prefix = "hi"
    name = nil

    def new() {}
	
    def __new__(n) {
        name = "<init>" + n
    }

    def of(pf) {
        prefix = pf
    }

    def say() {
        if nil != name {
            println(prefix + ", " + name + "!")
        } else {
            println(prefix + "!")
        }
    }

    def greet(name) {
        println(prefix + ", " + name + "!")
    }

    def __invoke__(name) {
        greet(name)
    }
}

g = Greeter("Eda")
g.greet("Roxa")
arr = [] 
i = 0
while i < 10 {
    arr[i] = Greeter("No_" + i)
    i = i + 1
}
i = 0
while i < 10 {
    arr[i].say()
    i = i + 1
}