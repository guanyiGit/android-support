function sendHttp(URL, TYPE, PARAMS) {
    var temp = document.createElement("form");
    temp.action = URL;
    if (TYPE != null && TYPE != '' && TYPE.toUpperCase() == "POST") {
        temp.method = "POST";
    } else {
        temp.method = "GET";
    }
    temp.style.display = "none";

    for (var x in PARAMS) {
        var opt = document.createElement("textarea");
        opt.name = x;
        opt.value = PARAMS[x];
        temp.appendChild(opt);
    }

    document.body.appendChild(temp);
    temp.submit();

    return temp;
}


function objectToFormData(obj, namespace, form) {
    var fd = form || new FormData();
    var formKey;
    if (obj instanceof Array) {
        for (var item of obj) {
            if (typeof item === 'object' && !(item instanceof File)) {
                objectToFormData(item, fd, namespace + '[]');
            } else {
                // 若是数组则在关键字后面加上[]
                fd.append(namespace + '[]', item)
            }
        }
    } else {
        for (var property in obj) {
            if (obj.hasOwnProperty(property) && obj[property]) {
                if (namespace) {
                    // 若是对象，则这样
                    formKey = namespace + '[' + property + ']';
                } else {
                    formKey = property;
                }
                // if the property is an object, but not a File,
                // use recursivity.
                if (typeof obj[property] === 'object' && !(obj[property] instanceof File)) {
                    // 此处将formKey递归下去很重要，因为数据结构会出现嵌套的情况
                    objectToFormData(obj[property], fd, formKey);
                } else {

                    // if it's a string or a File object
                    fd.append(formKey, obj[property]);
                }
            }
        }
    }
    return fd;

};

function makeFormData(obj, form_data) {
    var data = [];
    if (obj instanceof File) {
        data.push({key: "", value: obj});
    } else if (obj instanceof Array) {
        for (var j = 0, len = obj.length; j < len; j++) {
            var arr = makeFormData(obj[j]);
            for (var k = 0, l = arr.length; k < l; k++) {
                var key = !!form_data ? j + arr[k].key : "[" + j + "]" + arr[k].key;
                data.push({key: key, value: arr[k].value})
            }
        }
    } else if (typeof obj == 'object') {
        for (var j in obj) {
            var arr = makeFormData(obj[j]);
            for (var k = 0, l = arr.length; k < l; k++) {
                var key = !!form_data ? j + arr[k].key : "[" + j + "]" + arr[k].key;
                data.push({key: key, value: arr[k].value})
            }
        }
    } else {
        data.push({key: "", value: obj});
    }
    if (!!form_data) {
        // 封装
        for (var i = 0, len = data.length; i < len; i++) {
            form_data.append(data[i].key, data[i].value)
        }
    }
    else {
        return data;
    }
}

