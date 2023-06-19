const html = `
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
</head>
<body>
<h1>哈哈哈哈哈</h1>
</body>
<script src="https://eqcn.ajz.miesnfu.com/wp-content/plugins/wp-3d-pony/live2dw/lib/L2Dwidget.min.js"></script>

<script>
    L2Dwidget.init({
        "live2d": {
            jsonPath: "https://unpkg.com/live2d-widget-model-tororo/assets/tororo.model.json",
            "scale": 1
        },
        "display": {
            "position": "right", //看板娘的表现位置
            "width": 150,  //小萝莉的宽度
            "height": 300, //小萝莉的高度
            "hOffset": 0,
            "vOffset": -20
        },
        "mobile": {
            "show": true,
            "scale": 0.5
        },
        "react": {
            "opacityDefault": 0.7,
            "opacityOnHover": 0.2
        }
    });
</script>
</html>
`;
export default html;
