package main

import (
	"fmt"
	"math/rand"
)

var estados = []byte{'B','R','M'}
var estados_size int = len(estados)

func cadena_markov() [][]float64 {
    a := make([][]float64, estados_size)
    for i := range a {
        a[i] = make([]float64, estados_size)
    }
    
    for i := range a{
        for j := range a[i]{
            a[i][j] = 0
        }
    }
    return a
}

func buscar(estados []byte, estado byte) int{
    for i := 0; i < estados_size; i++ {
        if estado == estados[i] {
            return i
        }
    }
    return -1
}

func probabilidad(mat [][]float64, es []byte, in string) float64{
    var res float64 = 1.0
    var actual, actual2 int
    for len(in) > 1 {
        actual = buscar(es, in[0])
	actual2 = buscar(es, in[1])
	fmt.Println(in)
	if (actual != -1) && (actual2 != -1) {
	    res = res * mat[actual][actual2]
	}else{ return -1
	}
	
	in = in[1:len(in)]
    }

    return res
}

func probabilidad_al(mat [][]float64, es []byte, n int) [][]float64{
    var cont [][]float64 = cadena_markov()
    var a int = 0
    var f int = 0
    var t int = n
    var sum float64 = 0.0
    //var res float64 = 1.0
    var paso string = string(es[f])

    fmt.Printf("estado inicial: %c \n _________ \n", es[f]);

    r := rand.New(rand.NewSource(99))
    
    for t >= 0 {
        var ran float64 = r.Float64()
        for a < len(mat[f]){
            sum = sum + mat[f][a];
            if sum > ran {
                break;
            }
            a++
        }
 
        cont[f][a]++
        f = a
        paso += string(es[f])
        t--
        a = 0
        sum = 0.0
    }

    fmt.Printf("transiciones observadas \n")
    for i := 0; i < len(cont); i++ {
        for j := 0; j < len(cont[i]); j++ {
            fmt.Printf("%f ", cont[i][j])
        }
        fmt.Printf("\n")
    }
    return cont
}

func monte_carlo(cont [][]float64, i int, j int, c chan float64){
    var sum float64 = 0;
    for k := 0; k < len(cont[i]); k++ {
        sum += cont[i][k]
    }
    c <- cont[i][j]/sum
}

func monte_carlo_full(cont [][]float64){
    c := make(chan float64)

    for i := 0; i < len(cont); i++ {
        for j := 0; j < len(cont[i]); j++ {
            go monte_carlo(cont, i, j, c)
            mc := <-c
            fmt.Printf("%f ", mc)
        }
        fmt.Printf("\n")
    }
}

type Matriz struct{
    mark [][]float64
}

func main() {
    
    markov_cont := Matriz{}
    markov := Matriz{}
 
    markov_cont.mark = cadena_markov()
    markov.mark = cadena_markov()
    markov.mark[0][0] = 0.2
    markov.mark[0][1] = 0.3
    markov.mark[0][2] = 0.5
    markov.mark[1][0] = 0.3
    markov.mark[1][1] = 0.4
    markov.mark[1][2] = 0.3
    markov.mark[2][0] = 0.5
    markov.mark[2][1] = 0.1
    markov.mark[2][2] = 0.4

    var prob float64 = probabilidad(markov.mark, estados, "MMRBBB")
    fmt.Println(prob)

    /*markov_cont.mark = probabilidad_al(markov.mark, estados, 10000)
    fmt.Printf("\n Montecarlo : \n")
    monte_carlo_full(markov_cont.mark)*/
}