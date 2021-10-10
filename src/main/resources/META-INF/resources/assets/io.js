http = {
    get: (path, success, error) => {
        http.request('GET', path, null, success, error);
    },
    put: (path, body, success, error) => {
        http.request('PUT', path, body, success, error);
    },
    request: (method, path, body, success, error) => {
        let request = new XMLHttpRequest();
        request.onreadystatechange = () => {
            if (request.readyState == 4) {
                console.log(request.getAllResponseHeaders());
            }
        }
        request.open(method, path, true);
        request.setRequestHeader('Accept', 'application/json');
        request.setRequestHeader('Content-Type', 'application/json');
        request.send(JSON.stringify(body));
    }
};