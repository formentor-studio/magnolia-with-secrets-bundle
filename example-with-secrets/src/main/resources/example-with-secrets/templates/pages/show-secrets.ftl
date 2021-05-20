[#assign title = content.title!"Dummy page (created by maven archetype)"]
<!DOCTYPE html>
<html>
<head>
    <title>${title}</title>

    <link rel="stylesheet" href="${ctx.contextPath}/.resources/example-with-secrets/webresources/css/style.css">

[@cms.page /]
</head>
<body>
<div class="container">
    <header>
        <h1>Secrets</h1>
    </header>
    <p>Value of secret <strong>vendorApiKey</strong> is <strong>${model.getVendorApiKey()!}<strong></p>
</div>
</body>
</html>
