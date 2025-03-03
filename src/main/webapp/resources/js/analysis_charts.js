document.addEventListener("DOMContentLoaded", function () {
    var gananciasCtx = document.getElementById("gananciasChart").getContext("2d");
    new Chart(gananciasCtx, {
        type: "line",
        data: {
            labels: ["Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio"],
            datasets: [{
                label: "Ganancias",
                data: [10000, 15000, 12000, 18000, 22000, 25000],
                borderColor: "green",
                fill: false
            }, {
                label: "Pérdidas",
                data: [5000, 4000, 6000, 5000, 4500, 3000],
                borderColor: "red",
                fill: false
            }]
        }
    });

    var categoriasCtx = document.getElementById("categoriasChart").getContext("2d");
    new Chart(categoriasCtx, {
        type: "bar",
        data: {
            labels: ["Comida", "Limpieza", "Hogar", "Electrónica"],
            datasets: [{
                label: "Ventas por Categoría",
                data: [40000, 25000, 15000, 10000],
                backgroundColor: "blue"
            }]
        }
    });
});