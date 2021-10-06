<#import "parts/orderpages/ordersCommon.ftl" as c>

<@c.orderPage>
    <main class="w-50 p-3">
        <div class="card-columns">
            <#list employees as employee>
                <div class="card">
                    <#--                <#if employee.filename??>-->
                    <#--                    <img src="/img/${employee.filename}" class="card-img-top">-->
                    <#--                </#if>-->
                    <div class="card-body">
                        <span>${employee.getFirstName()}</span>
                        <i>${employee.getLastName()}</i>
                    </div>
                    <div class="card-footer text-muted">
                        ${employee.getFirstName()}
                        <a href="#" class="btn btn-primary">Go somewhere</a>
                    </div>
                </div>
            <#else>
                No message
            </#list>
        </div>
    </main>

</@c.orderPage>