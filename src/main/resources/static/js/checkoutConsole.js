import {html, render} from 'https://unpkg.com/lit-html?module';
import {styleMap} from 'https://unpkg.com/lit-html/directives/style-map?module';

let row = (data, handler) =>
    html`
        <tr>
            <th>
                <input class="form-check-input align-middle" type="checkbox" @click=${handler} value="" id=${data.orderId} ?disabled=${data.checkDisabled}>
            </th>
            <th>
                <img class="payment-item-image align-middle"
                     src=${data.imageUrl}>
            </th>

            <th><span>${data.name}</span></th>
            <th><span .textContent=${data.payer == null ? '' : data.payer}></span></th>
            <th><span>${data.price}</span></th>
        </tr>`;

let container = document.getElementById("checkout-container");

async function display() {
    let http = await fetch("http://91.139.199.150/user/checkout/orders");
    let json = await http.json();
    let templates = [];
    console.log(json);
    for (const order of json) {
        templates.push(row(order, handler));
    }
    render(templates, container);
}

function handler(e) {
    let orders = Array.from(container.querySelectorAll("input:checked:not([disabled])"))
        .map(i => i.id);
    let body = {
        orders
    };
    console.log(body);
    fetch("http://91.139.199.150/user/checkout/check", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(body)
    })
}

display();



