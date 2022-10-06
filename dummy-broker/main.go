package main

import (
    "fmt"
    "net/http"
)

func main() {
    http.HandleFunc("/", DummyResponse)
    http.ListenAndServe(":9080", nil)
}

func DummyResponse(w http.ResponseWriter, r *http.Request) {
    fmt.Fprintf(w, "{}")
}