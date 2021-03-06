import {html, render} from 'https://unpkg.com/lit-html?module';
import {styleMap} from 'https://unpkg.com/lit-html/directives/style-map?module';
import {address} from "./fetchApi.js";
import {fetchesDisabled} from "./disablePostLogoutFetches.js";

let card = (order, handler, cooking = true, handler2) => html`
    <div class="col-lg-3 mt-3">
        <div class="card rounded-back bg-dark pt-4">
            <img src=${order.imageUrl}/>
            <div class="card-body text-center text-light">
                <h5 class="item-name">${order.name}</h5>
                <h6>Маса №${order.tableView.number}</h6>

                <a class="btn btn-primary mb-3" data-bs-toggle="collapse" href=${'#collapse' + order.id}
                   role="button" aria-expanded="false">Бележка</a>
                
                <button style=${styleMap({display: cooking ? 'none' : 'inline'})} @click=${handler} id="acceptButton" class="btn btn-primary mb-3">Поеми</button>
                <button style=${styleMap({display: cooking ? 'inline' : 'none'})} @click=${handler} id="readyButton" class="btn btn-primary mb-3">Готово</button>
                <button style=${styleMap({display: cooking ? 'inline' : 'none'})} @click=${handler2} id="cancelButton" class="btn btn-primary mb-3">Откажи</button>
                <div class="collapse" id=${'collapse' + order.id}>
                    <div>${order.description}</div>
                </div>

                <div id="orderId" hidden>${order.id}</div>

            </div>
        </div>
    </div>
`;

let waitingContainer = document.getElementById("waitingContainer");
let currentWaiterContainer = document.getElementById("currentWaiterContainer");

async function doFetch(){
    let http = await fetch(address + "/personnel/waiter/orders-rest");
    let json = await http.json();
    let templates = [];
    for (const order of json) {
        templates.push(card(order, acceptHandler, false));
    }
    render(templates, waitingContainer);

    http = await fetch(address + "/personnel/waiter/current/orders-rest");
    json = await http.json();

    templates = [];
    for (const order of json) {
        templates.push(card(order, orderReadyHandler, true, cancelOrderHandler));
    }
    render(templates, currentWaiterContainer);
}

let lastFetchDone = true;
async function display() {
    if(lastFetchDone && !fetchesDisabled){
        lastFetchDone = false;
        doFetch().then(() => lastFetchDone = true);
    }
}

function acceptHandler(e) {
    let orderId = e.target.parentNode.querySelector("#orderId").textContent.trim();
    fetch(address + "/personnel/waiter/order/" + orderId + "/accept-rest");
}

function orderReadyHandler(e) {
    let orderId = e.target.parentNode.querySelector("#orderId").textContent.trim();
    fetch(address + "/personnel/waiter/order/" + orderId + "/ready-rest");
}

function  cancelOrderHandler(e){
    let orderId = e.target.parentNode.querySelector("#orderId").textContent.trim();
    fetch(address + "/personnel/waiter/order/" + orderId + "/cancel-rest");
}

display();

setInterval(display, 200);