#show_log false
class Greeter{prefix = "hi"; def new() {}; def of(pf) {prefix = pf}; def say() {prefix + "!"}; def greet(name) {println(prefix + ", " + name + "!")};  def __invoke__(name) {greet(name)}}

#show_log false
class Greeter{
    prefix = "hi"
    name = nil

    def new() {}
	
    def __new__(name) {
        prefix = "<init>" + name
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

g = Greeter.new()
g.say
g.say()
g.prefix = ""
g.greet("Len")
name = "Rin"
g.greet(name)
#g.greet "Rin"

h = "hello"
println("start: " + (start = currentTimeMillis()))
counter = 100000
while counter > 0 {
    counter = counter - 1
}
println("end: " + (end = currentTimeMillis()))
println("cost: " + (end - start))


arr = [1,2,3]

arr[0]


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