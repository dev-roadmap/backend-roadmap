let counter = 0;
const requestHeaders = {
    'Accept': 'application/json',
    'Content-Type': 'application/json'
};
http = {
    header: (key, value) => {
        requestHeaders[key] = value;
    },
    get: (path, success, error) => {
        http.request('GET', path, null, success, error);
    },
    put: (path, body, success, error) => {
        http.request('PUT', path, body, success, error);
    },
    post: (path, body, success, error) => {
        http.request('POST', path, body, success, error);
    },
    request: (method, path, body, success, error) => {

        let request = new XMLHttpRequest();
        request.onreadystatechange = () => {
            // <li id="request-template" className="list-group-item d-flex justify-content-between lh-sm">
            //     <div>
            //         <h6 className="my-0">Request</h6>
            //         <div><label>Content-Type</label>:&nbsp;<span>JSON </span></div>
            //         <div>
            //             <pre>{}</pre>
            //         </div>
            //         <h6 className="my-0">Response</h6>
            //         <div><label>Content-Type</label>:&nbsp;<span>JSON </span></div>
            //         <div>
            //             <pre>{}</pre>
            //         </div>
            //     </div>
            // </li>
            if (request.readyState == 4) {
                let headers = request.getAllResponseHeaders()
                    .split('\n')
                    .map(header => header.substr(0, header.indexOf(':')))
                    .filter(header => header != null && header.length > 0);
                console.log(headers);
                console.log(request.getAllResponseHeaders());
                console.log(request.responseText);
                let container = document.createElement('li');
                container.classList.add('list-group-item', 'd-flex', 'justify-content-between', 'lh-sm');
                let containerDiv = document.createElement('div');

                function createHeader(key, value) {
                    let headerContainer = document.createElement('div');
                    let keyLabel = document.createElement('label');
                    keyLabel.innerText = key;
                    headerContainer.appendChild(keyLabel);
                    headerContainer.appendChild(document.createTextNode(":\u00A0"))
                    headerContainer.appendChild(document.createTextNode(value));
                    headerContainer.style['word-wrap'] = 'break-word';
                    return headerContainer;
                }

                function createBody(body) {
                    let bodyContainer = document.createElement('div');
                    let pre = document.createElement('pre');
                    pre.innerText = JSON.stringify(body, null, 4);
                    bodyContainer.appendChild(pre);
                    return bodyContainer;
                }

                let requestHeader = document.createElement('h6');
                requestHeader.classList.add('my-0');
                requestHeader.innerText = 'Request';
                containerDiv.appendChild(requestHeader);

                for (let key in requestHeaders) {
                    containerDiv.appendChild(createHeader(key, requestHeaders[key]));
                }

                if (body != null) {
                    containerDiv.appendChild(createBody(body));
                }

                let responseHeader = document.createElement('h6');
                responseHeader.classList.add('my-0');
                responseHeader.innerText = 'Response';
                containerDiv.appendChild(responseHeader);

                headers.forEach(key => {
                    containerDiv.appendChild(createHeader(key, request.getResponseHeader(key)));
                });

                if (request.responseText.length > 0) {
                    containerDiv.appendChild(createBody(JSON.parse(request.responseText)));
                }
                container.appendChild(containerDiv);
                $('#request-log').append(container);
                $('#http-contador').text(++counter);
                if (request.status >= 200 && request.status <= 299) {
                    success(JSON.parse(request.responseText))
                }
                if ((request.status >= 400 && request.status <= 499) ||
                    (request.status >= 500 && request.status <= 599)) {
                    error(JSON.parse(request.responseText))
                }
            }
        }
        request.open(method, path, true);
        for (let key in requestHeaders) {
            request.setRequestHeader(key, requestHeaders[key]);
        }
        request.send(JSON.stringify(body));
    }
};