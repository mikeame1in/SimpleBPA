<#import "../parts/orders/ordersCommon.ftl" as c>

<@c.orderPage>
    <ul class="nav flex-column">
        <li class="nav-item">
            <a class="nav-link active" href="#">Active</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="#">Link</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="#">Link</a>
        </li>
        <li class="nav-item">
            <a class="nav-link disabled" href="#">Disabled</a>
        </li>
    </ul>
<#list orders as order>
<div>
    <b>${order.orderNumber}</b>
    <span>${order.acceptanceDate}</span>
</div>
<#else>
No message
</#list>
</@c.orderPage>