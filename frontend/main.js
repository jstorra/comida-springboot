const main = document.querySelector("main");
const sectionCards = document.querySelector("section")
const form = document.querySelector("form")
const api = "http://localhost:8080"

document.addEventListener("DOMContentLoaded", async () => {
    const data = await (await fetch(api)).json();

    form.addEventListener("submit", async (e) => {
        e.preventDefault();
        const data = Object.fromEntries(new FormData(e.target))
        let config = {
            method: "POST",
            headers: { "content-type": "application/json" },
            body: JSON.stringify(data),
        };
        if (btnS.textContent === "Actualizar Plato") {
            let id = btnS.dataset.edit;
            data["id"] = Number(id)
            let config = {
                method: "PUT",
                headers: { "content-type": "application/json" },
                body: JSON.stringify(data),
            };
            await fetch(api + "/update", config);
          } else await fetch(api + "/save", config);
          window.location.reload();
    })

    const cards = data.map(plato => 
        `
        <div class="card">
            <img src="${plato.imagen}" />
            <h3 class="nombre">${plato.nombre}</h3>
            <p class="descripcion">${plato.descripcion}</p>
            <p class="precio">Precio: <span>$${plato.precio}</span></p>
            <div class="buttons">
                <button data-mod="${plato.id}" class="btn-modificar"><svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="currentColor" class="bi bi-pencil-square" viewBox="0 0 16 16">
                <path d="M15.502 1.94a.5.5 0 0 1 0 .706L14.459 3.69l-2-2L13.502.646a.5.5 0 0 1 .707 0l1.293 1.293zm-1.75 2.456-2-2L4.939 9.21a.5.5 0 0 0-.121.196l-.805 2.414a.25.25 0 0 0 .316.316l2.414-.805a.5.5 0 0 0 .196-.12l6.813-6.814z"/>
                <path fill-rule="evenodd" d="M1 13.5A1.5 1.5 0 0 0 2.5 15h11a1.5 1.5 0 0 0 1.5-1.5v-6a.5.5 0 0 0-1 0v6a.5.5 0 0 1-.5.5h-11a.5.5 0 0 1-.5-.5v-11a.5.5 0 0 1 .5-.5H9a.5.5 0 0 0 0-1H2.5A1.5 1.5 0 0 0 1 2.5v11z"/>
                </svg>
                </button>
                <button data-del="${plato.id}" class="btn-eliminar"><svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="currentColor" class="bi bi-trash" viewBox="0 0 16 16">
                <path d="M5.5 5.5A.5.5 0 0 1 6 6v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5Zm2.5 0a.5.5 0 0 1 .5.5v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5Zm3 .5a.5.5 0 0 0-1 0v6a.5.5 0 0 0 1 0V6Z"/>
                <path d="M14.5 3a1 1 0 0 1-1 1H13v9a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V4h-.5a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1H6a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1h3.5a1 1 0 0 1 1 1v1ZM4.118 4 4 4.059V13a1 1 0 0 0 1 1h6a1 1 0 0 0 1-1V4.059L11.882 4H4.118ZM2.5 3h11V2h-11v1Z"/>
                </svg>
                </button>
            </div>
        </div>
        `).join("")

    sectionCards.innerHTML = cards

    const eliminarbtns = document.querySelectorAll(".btn-eliminar")
    const modificarbtns = document.querySelectorAll(".btn-modificar")
    const btnS = document.querySelector(".btnS")

    eliminarbtns.forEach((btn) => {
        let id = btn.dataset.del;
        let config = {
          method: "DELETE",
        };
        btn.addEventListener("click", async () => {
          await fetch(api + "/delete/" + id, config);
          window.location.reload();
        });
      });

    modificarbtns.forEach((btn) => {
        let id = btn.dataset.mod;
        btn.addEventListener("click", async () => {
            btnS.textContent = "Actualizar Plato";
            btnS.setAttribute("data-edit", id);
            const plato = await (await fetch(api + "/get/" + id)).json();

            document.querySelector("#nombre").value = plato.nombre;
            document.querySelector("#descripcion").value = plato.descripcion;
            document.querySelector("#precio").value = plato.precio;
            document.querySelector("#imagen").value = plato.imagen;
        });
    });
})