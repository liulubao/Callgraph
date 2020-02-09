function getCookie(name) {
    var nameEQ = name + "=";
    var ca = document.cookie.split(';');
    for(var i=0;i < ca.length;i++) {
        var c = ca[i];
        while (c.charAt(0)==' ') c = c.substring(1,c.length);
        if (c.indexOf(nameEQ) == 0) return c.substring(nameEQ.length,c.length);
    }
    return null;
}
var data1 = null ;
var cy =null;  //依赖图的对象
var flag = false;
//选择类依赖/方法依赖/数据依赖/
$('#classdependency').click(function () {

    // location..reload();
    flag = false;
    document.getElementById("codecontent").style.visibility = "hidden";
    console.log("类yilai:" + data1.methodCall.length)
    graph(data1.classCall);
    $('#classdependency').addClass("active");
    $('#methoddependency').removeClass("active");
    $('#datadependency').removeClass("active");
})
$('#methoddependency').click(function () {
    document.getElementById("codecontent").innerText="";
    flag = true;
    document.getElementById("codecontent").style.visibility = "visible";
    console.log("类yilai:" + data1.methodCall.length)
    graph(data1.methodCall)
    $('#classdependency').removeClass("active");
    $('#methoddependency').addClass("active");
    $('#datadependency').removeClass("active");
})
$('#datadependency').click(function () {
    // flag = false;
    // document.getElementById("codecontent").style.visibility = "hidden";
    // graph(data1.dataName)
    // $('#classdependency').removeClass("active");
    // $('#methoddependency').removeClass("active");
    // $('#datadependency').addClass("active");
})
$(function () {
    //获取依赖数据
    var projectid = getCookie("dependency_projectid");
    $.ajax({
        type: 'GET',
        url: "/project/getdenpendenbyprojectid",
        // dataType: 'json',
        // contentType: 'application/json',
        <!-- headers: { 'userName': username, 'password': password }, -->
        data:{"id":projectid},
        async:false,
        success: function (data) {
            if(data.status==1){
                // alert("成功获得数据")
                bootbox.alert("分析成功");
                console.log(data);
                data1=data.data;
                console.log("data1 content"+data1);
                console.log(data1);
                graph(data1.methodCall);
            }else{
                bootbox.alert("分析失败:"+data.response);
            }

        },
        error: function(data) {
            console.log(data);
            bootbox.alert('获取数据失败！' + data);
        },
    })

})

//依赖图的主要函数
function graph(data) {
    console.log(data);
    $(".topnav li").remove();
    //模块颜色，从以下随机挑选
    var color = [
        "#39BDC4",
        "#20B427",
        "#03E7E6",
        "#CD3F90",
        "#8429AA",
        "#2DF009",
        "#E153D3",
        "#B7B6A3"
    ]
    //树状目录填充
    var elementArray = [];
    for(var i=0;i<data.length;i++){
        var str = data[i].data.className;
        var element = {};
        element['label'] = data[i].data.id;
        elementArray.push(element);
        // $('#mylist').append('<option>'+data[i].data.id+'</option>')
        if(!isnull(str)){
            console.log(str);
            fillMenu(str)
        }else{

        }
    }
    $(".topnav").accordion({
        accordion:false,
        speed: 500,
        closedSign: '[+]',
        openedSign: '[-]'
    });
    $("#local2").autocomplete({
        source: elementArray,
        max:5
    });

    var tippyA = null;
    //cytoscepe，依赖图的展示对象
    cy = cytoscape({
        container: document.getElementById('cy'),
        boxSelectionEnabled: true,
        autounselectify: true,
        motionBlur:!1,
        maxZoom:2.5,
        minZoom:0.4,
        wheelSensitivity:.1,//滑动滚轮一次缩放大小
        textureOnViewport:!1,

        layout: {
            // name: 'spread',
            // minDist: 40
            // name: 'klay'
            // name: 'cola'
            name: 'cose-bilkent',
            animate: false
            // name: 'grid',
            // name: 'spread',
            // minDist: 40
            // name: 'euler',
            // randomize: true,
            // animate: true
            // rankDir: 'LR'
            // name: 'dagre'
            // rankDir: 'LR'
        },
        style: [
            {
                selector: 'node',
                css: {
                    // "text-valign": 'center',
                    'background-color': '#61BFFC',
                    'text-valign': 'top',
                    'text-halign': 'top',
                }
            },
            {
                selector: ':parent',
                style: {
                    'background-color': '#000000',
                    'background-opacity': 0.333
                }
            },
            {
                selector: 'edge',
                css: {
                    'line-color': '#61BFFC'
                }
            },
            {
                selector: ".nodeHover", //节点变暗，有悬停效果
                style: {
                    "content": function (ele) {
                        return ele.data('id').indexOf(':')!=-1?ele.data('id').split(':')[1]:ele.data('id');
                    },
                }
            },
            {
                selector: ".nodeActive",
                style: {
                    'background-color': '#FF0000',
                    "width": 60,
                    "height": 60,
                    "z-index": 1,
                    // "opacity": .2
                }
            },
            {
                selector: ".edgeShow",
                style: {
                    'backgroud-colr': '#000000',
                    "color": "#000000",
                },
            },
            {
                selector: ".edgeActive",
                style: {
                    "line-style": function (a) {
                        return "solid"
                    },
                    // "arrow-scale": .8,
                    "width": 1.5,
                    "color": "#330000",
                    "text-opacity": 1,
                    "font-size": 10,
                    "text-background-color": "#fff",
                    "text-background-opacity": .8,
                    "text-background-padding": 0,
                    "source-text-margin-y": 20,
                    "target-text-margin-y": 20,
                    "z-index-compare": "manual",
                    "z-index": 1,
                    "line-color": "#FF0000",
                }
            },
            {
                selector: ".dull",
                style: {
                    "width": 60,
                    "height": 60,
                    "z-index": 1,
                    "opacity": .2
                }
            }
        ],
        elements: data
    })
    //删除单独的结点
    var deletenode = cy.filter(function(element, i){
        return element.isNode() && element.indegree()== 0 && element.outdegree()== 0;
    });
    cy.remove(deletenode)

    var oriNodes = cy.nodes();
    var oriEdges = cy.edges();
    var coll = cy.collection();
    cy.add(coll);
    var nodes = cy.nodes()
    cy.collection("edge").addClass("edgeShow");

    cy.on("tapend", "node", function (a) { //监听鼠标左键释放
        let c = a.target;

        cy.collection("edge").removeClass('dull');
        cy.collection("node").removeClass('dull');
        c.removeClass('nodeActive');
        c.neighborhood("edge").removeClass("edgeActive");
    })

    var node = cy.nodes();
    //结点提示框
    var makeTippy = function(node, text){
        if(text.startsWith("module")){
            return tippy( node.popperRef(), {
                // theme:'light',
                html: (function(){
                    var div = document.createElement('div');
                    div.innerHTML = "<span style='display: none'>"+text+ "</span><br/><button id='detail'  class=\"btn btn-default\" style='float:left;margin-left: 1px'>详情</button><button onclick='confirm(\"你点击了取消按钮\")' class=\"btn btn-default\" style='float:right;margin-right: 1px'>取消</button>";
                    div.style.color= "black";
                    // div.style.backgroundColor = "white";
                    // div.style.height=60;
                    // div.style.width=60;
                    return div;
                })(),
                // content: "test",
                trigger: 'manual',
                arrow: true,
                // arrowType:'sharp',
                placement: 'bottom',
                hideOnClick: false,
                multiple: true,
                sticky: true,
                interactive:'true',
                theme: 'light'
            } ).tooltips[0];

        }else{
            return tippy( node.popperRef(), {
                theme:'light',
                html: (function(){
                    // var div = document.getElementById('myTemplate');
                    var div = document.createElement('div');
                    div.style.width= "200px";
                    div.style.height= "230px";
                    div.style.position="relative"
                    div.innerHTML =  "<h4 style='float:left;'>标题</h4><br><input id='labeltitle' type=\"text\" class=\"form-control\">" +
                        "<h4 style='float:left;'>内容</h4><br><textarea id='labelcontent' class=\"form-control\" rows='3'></textarea>" +
                        "<button id='labeldetails' class='btn btn-info' style='float: right;margin: 2px'>详情</button><button id='labelsubmit' class='btn btn-info' style='float: right;margin: 2px;margin-right: 0px'>提交</button>"
                    var button =document.createElement('button');
                    div.appendChild(button);
                    return div;
                })(),
                // content: container.innerHTML,
                trigger: 'manual',
                arrow: true,
                // arrowType:'sharp',
                placement: 'bottom',
                hideOnClick: false,
                multiple: true,
                sticky: true,
                interactive:'true',
                theme: 'light'
            } ).tooltips[0];
        }

    };
    var divnum = 0
    //结点点击事件
    cy.nodes().on("click", function (a) {
        if(tippyA!=null){
            tippyA.hide();
        }
        let c = a.target;
        $('#nodeinfo').text(c.data('id'));
        var idstr = c.data('id');
        tippyA = makeTippy(c,c.data('id'));
        tippyA.show();

        if(idstr.startsWith('module')){
            var allnodes = cy.nodes(); //模块点击事件
            var alledges = cy.edges();
            var btn = document.getElementById('detail');
            console.log("test0");
            btn.onclick=function (ev) {
                console.log("test1");
                $("#tab-content div:first-child").removeClass("active");
                $("ul li:first-child").removeClass("active")
                tippyA.hide();
                $("#tab-content").append("<div id=\"cy"+divnum+"\" class=\"tab-pane active\" style='height: 100%;width: 100%'></div>")
                $("#contentnavid").append("<li role=\"presentation\" class=\"active\"><a href=\"#cy"+divnum+"\" aria-controls=\"cy\" role=\"tab\" data-toggle=\"tab\">"+idstr+"&nbsp;<span class=\"glyphicon glyphicon-remove\" onclick='test(this)'></span></a></li>")
                var cy1 = cytoscape({
                    container: document.getElementById('cy'+divnum++),
                    boxSelectionEnabled: true,
                    autounselectify: true,
                    motionBlur:!1,
                    maxZoom:2.5,
                    minZoom:0.4,
                    wheelSensitivity:.1,//滑动滚轮一次缩放大小
                    textureOnViewport:!1,
                    layout: {
                        name: 'klay'
                    },
                    style: [
                        {
                            selector: 'node',
                            css: {
                                "content": function (ele) {
                                    return ele.data('label') || ele.data('id')
                                },
                                "text-valign": 'center',
                                "width": function (a) {
                                    return a.data('id').length*3.5;
                                },
                                "height": function (a) {
                                    return 45
                                },
                                "background-color": function (a) {
                                    return '#fff'
                                },
                                "color": '#111111',
                                "border-color": function (a) {
                                    return '#47D2AC'
                                },
                                'background-image': "url(./gline.png)",
                                "border-width": 1,
                                "text-wrap": "wrap",
                                "font-size": 10,
                                "font-family": "microsoft yahei",
                                "shape": "rectangle",
                                "padding": function (a) {
                                    return -6
                                },
                                "text-margin-y": function (a) {
                                    return -4
                                },
                                "label": function (a) {
                                    a = a.data("id");
                                    var b = a.split(":");
                                    return b[0]+'\n \n'+b[1];
                                },
                            }
                        },
                        {
                            selector: 'edge',
                            css: {
                                // "line-style": function (a) {
                                //     return "solid"
                                // },
                                // "curve-style": "bezier",
                                // "control-point-step-size": 20,
                                // "target-arrow-shape": "triangle",
                                // "target-arrow-color": function (a) {
                                //     // return a.data("color")
                                //     return '#DCDCDC'
                                // },
                                'line-color': '#47D2AC'
                            }
                        }
                    ],
                })

                var colall = cy.collection();
                var col = cy.nodes('[parent = "'+idstr+'"]')
                colall.merge( cy.nodes() );
                colall.unmerge(a.target);
                colall.unmerge(col);
                cy.remove(colall);

                var lnodes=cy.collection();
                var ledges =cy.edges();
                cy.nodes().forEach(function (ele) {
                    if(ele.data('id').startsWith("module")){
                        cy.remove(cy.getElementById(ele.data('id')))
                    }else{
                        var nodeData = [
                            {"data":{"id": ele.data('id'),"className":ele.data('className'),"methodName":ele.data('methodName'),"arguments":ele.data('arguments')}},
                        ]
                        cy.remove(cy.getElementById(ele.data('id')))
                        cy.add(nodeData);
                    }
                })
                console.log("test2");
                cy.add(ledges)
                var layout = cy.layout({name: 'klay'})
                layout.run();

                cy1.add(cy.nodes());
                cy1.add(cy.edges());
                cy.remove(cy.nodes())
                cy.remove(cy.edges())
                cy.add(allnodes);
                cy.add(alledges);
                var layout = cy.layout({
                    name: 'cose-bilkent',
                    animate: false})
                layout.run();
                var test = document.getElementById("cy0");
                test.nodes().on("click", function (a) {
                    alert('wade');
                });
            }
        }else{

            var detailbtn = document.getElementById('labeldetails');//结点点击事件
            detailbtn.onclick=function (ev) {
                // alert(idstr);
                var data = {"parentelement":idstr,"projectid":getCookie("dependency_projectid")};
                // alert('ss: ' +idstr);
                getlal(data);

                if(flag = true ){

                    // getcode(idstr) //不能删除，该方法目的为获得结点的代码，待完善。
                }

            }
            var submitbtn = document.getElementById('labelsubmit');
            submitbtn.onclick=function (ev) {
                // alert(idstr);
                var title = $('#labeltitle').val();
                var content = $('#labelcontent').val();
                if (isnull(title) || isnull(content)){
                    bootbox.alert('标题和内容均两者均不能为空！');
                }else{
                    var projectid = getCookie("dependency_projectid");
                    var parentelement = idstr;
                    var ownername = getCookie("dependency_loginname");
                    if(isnull(ownername)){
                        bootbox.alert('您还没有登陆，不能够保存label！');
                    }else {
                        $.ajax({
                            type: 'POST',
                            url: "/label/savelabel",
                            // dataType: 'json',
                            contentType: 'application/json',
                            <!-- headers: { 'userName': username, 'password': password }, -->
                            data:'{"title":"'+title+'","content":"'+content+'","projectid":"'+projectid+'","parentelement":"'+parentelement+'","ownername":"'+ownername+'"}',
                            success: function (data) {
                                if(data.status==1){
                                    bootbox.alert('label保存成功！')
                                    var data = {"parentelement":idstr,"projectid":getCookie("dependency_projectid")};
                                    getlal(data);
                                }else{
                                    bootbox.alert('label保存失败！');
                                }

                            },
                            error: function(data) {
                                console.log(data);
                                bootbox.alert('保存失败！' + data);
                            },
                        })
                    }
                }
            }
        }

    })
    //获得结点具体代码，待完善
    function getcode(data) {
        $.ajax({
            type: 'POST',
            url: "http://localhost:9092/getcode",
            // dataType: 'json',
            // contentType: 'application/json',
            <!-- headers: { 'userName': username, 'password': password }, -->
            data:{"codename":data},
            success: function (data) {
                if(data.status==1){

                    document.getElementById("codecontent").innerText=data.content;
                }else{
                    // bootbox.alert('代码获取失败'+data);
                    document.getElementById("codecontent").innerText="";
                }

            },
            error: function(data) {
                console.log(data);
                document.getElementById("codecontent").innerText="";
                // bootbox.alert('代码获取失败获取详情失败！' + data);
            },
        })
    }
    //获取结点的label
    function getlal(data){
        $.ajax({
            type: 'GET',
            url: "/label/getbypidandparentelement",
            data:data,
            success: function (data) {
                if(data.status==1){
                    // alert('获取成功');
                    if (data.data.length == 0){
                        bootbox.alert("该结点暂时没有label");
                    }else{
                        bootbox.alert('label获取成功');
                        console.log(data.data);
                        addlabel(data.data);
                    }

                }else{
                    bootbox.alert('获取详情失败'+data);
                }

            },
            error: function(data) {
                console.log(data);
                bootbox.alert('获取详情失败！' + data);
            },
        })
    }

    cy.on("mouseover", "node", function (a) {
        cy.collection("node").removeClass('nodeHover');
        let c = a.target;
        c.addClass("nodeHover");
        if(tippyA!=null){
            tippyA.hide();
        }

    })

    cy.on("mouseout", "node", function (a) {
        let c = a.target;
        c.removeClass("nodeHover");

    })

    // cy.on("mouseout", "node", function (a) {
    //     // $('#cy').css('cursor', 'default');
    //     // document.getElementById('cy').css('cursor', 'default');
    //     let c = a.target;
    //     // c.removeClass("nodeHover");
    //     // cy.collection("edge").removeClass("edgeActive");
    //     // makeTippy(c, c.data('id')).hide();
    //     // tippyA.hide();
    //     // a.hide();
    // })

    var nodecollection = cy.filter(function(element, i){
        return element.isNode() && element.indegree()>2;
    });
    var nodelist = cy.filter(function(element, i){
        return element.isNode() && element.indegree()==0 && element.outdegree()==0;
    });

    var edgelist ;
    //查找的点击事件
    $("#search").click(function(){
        var midnode = cy.nodes();
        cy.remove(midnode);
        cy.add(data);
        var start = $(" #start ").val();
        var end = $(" #end ").val();
        if(isnull(start)||start>1){
            start=0;
        }
        if(isnull(end)||end<0){
            end=1
        }

        var nodes = cy.collection();

        var oriEdge = cy.filter(function(element, i){
            return element.isEdge() ;
            // return element.isEdge() && (2/(element.sources().outdegree()+element.target().indegree())) > start && (2/(element.sources().outdegree()+element.target().indegree())) < end;
        });

        var edge = cy.filter(function(element, i){
            return element.isEdge() && (element.data('label') < start || element.data('label') > end);
            // return element.isEdge() && (2/(element.sources().outdegree()+element.target().indegree())) > start && (2/(element.sources().outdegree()+element.target().indegree())) < end;
        });
        cy.remove(edge);


        //删除出入度为0的结点
        var node11 = cy.filter(function(element, i){
            return element.isNode() && element.indegree()== 0 && element.outdegree()== 0;
        });
        cy.remove(node11)

        var parentNum = 0;

        while(cy.nodes().length != 0){ //为结点划分模块
            var parentData = [
                {"data":{"id":"module"+parentNum}},
            ]
            cy.add(parentData);
            nodes.merge(cy.getElementById("module"+parentNum));
            var neighnodes = cy.nodes()[0];
            // alert(cy.nodes().length)
            while(neighnodes.length != 0){
                // var midneigh
                var midneigh = neighnodes.neighborhood().nodes();
                neighnodes.forEach(function (ele) {
                    var test = "module"+parentNum
                    var nodeData = [
                        {"data":{"parent":"module"+parentNum,"id": ele.data('id'),"className":ele.data('className'),"methodName":ele.data('methodName'),"arguments":ele.data('arguments')}},
                    ]
                    cy.remove(cy.getElementById(ele.data('id')));
                    cy.add(nodeData)
                    // console.log(ele.data('id'));
                    nodes.merge(cy.getElementById(ele.data('id')));
                    cy.remove(cy.getElementById(ele.data('id')))
                    // nodes.add(neighnodes)
                })
                neighnodes = midneigh;
            }
            cy.remove(cy.getElementById("module"+parentNum))
            parentNum++;
            // var neighnodes = node.neighborhood().nodes();

        }
        cy.add(nodes);
        cy.add(node11)

        cy.add(oriEdge);
        cy.add(edge);
        var layout = cy.layout({
            name: 'klay'

        });
        layout.run();
        for(var i=0;i<parentNum;i++){
            cy.getElementById('module'+i).style('background-color',color[i%8]);
        }

        cy.nodes().on("click", function (a) {
            if(tippyA!=null){
                tippyA.hide();
            }
            let c = a.target;
            $('#nodeinfo').text(c.data('id'));
            var idstr = c.data('id');
            console.log("a"+c);

            tippyA = makeTippy(c,c.data('id'));
            tippyA.show();
            if(idstr.startsWith('module')){

                var allnodes = cy.nodes();
                var alledges = cy.edges();
                var btn = document.getElementById('detail');
                btn.onclick=function (ev) {
                    alert("159")
                    $("#tab-content div:first-child").removeClass("active");
                    $("ul li:first-child").removeClass("active")
                    tippyA.hide();
                    // $("#tab-content li:first-child").removeClass("active")
                    // $("#contentnavid div:first-child").removeClass("active")
                    $("#tab-content").append("<div id=\"cy"+divnum+"\" class=\"tab-pane active\" style='height: 100%;width: 100%'></div>")
                    $("#contentnavid").append("<li role=\"presentation\" class=\"active\"><a href=\"#cy"+divnum+"\" aria-controls=\"cy\" role=\"tab\" data-toggle=\"tab\">"+idstr+"&nbsp;<span class=\"glyphicon glyphicon-remove\" onclick='test(this)'></span></a></li>")
                    var cy1 = cytoscape({
                        container: document.getElementById('cy'+divnum++),
                        boxSelectionEnabled: true,
                        autounselectify: true,
                        motionBlur:!1,
                        maxZoom:2.5,
                        minZoom:0.4,
                        wheelSensitivity:.1,//滑动滚轮一次缩放大小
                        textureOnViewport:!1,
                        layout: {
                            name: 'klay'
                        },
                        style: [
                            {
                                selector: 'node',
                                css: {
                                    "content": function (ele) {
                                        return ele.data('label') || ele.data('id')
                                    },
                                    "text-valign": 'center',
                                    "width": function (a) {
                                        return a.data('id').length*3.5;
                                    },
                                    "height": function (a) {
                                        return 45
                                    },
                                    "background-color": function (a) {
                                        return '#fff'
                                    },
                                    "color": '#111111',
                                    "border-color": function (a) {
                                        return '#47D2AC'
                                    },
                                    'background-image': "url(./gline.png)",
                                    "border-width": 1,
                                    "text-wrap": "wrap",
                                    "font-size": 10,
                                    "font-family": "microsoft yahei",
                                    "shape": "rectangle",
                                    "label": function (a) {
                                        a = a.data("id");
                                        var b = a.split(":");
                                        return b[0]+'\n \n'+b[1];
                                    },
                                }
                            },
                            {
                                selector: 'edge',
                                css: {
                                    'line-color': '#47D2AC'
                                }
                            }
                        ],
                    })

                    var colall = cy.collection();
                    var col = cy.nodes('[parent = "'+idstr+'"]')
                    colall.merge( cy.nodes() );
                    colall.unmerge(a.target);
                    colall.unmerge(col);
                    cy.remove(colall);

                    var lnodes=cy.collection();
                    var ledges =cy.edges();
                    cy.nodes().forEach(function (ele) {
                        if(ele.data('id').startsWith("module")){
                            cy.remove(cy.getElementById(ele.data('id')))
                        }else{
                            var nodeData = [
                                {"data":{"id": ele.data('id'),"className":ele.data('className'),"methodName":ele.data('methodName'),"arguments":ele.data('arguments')}},
                            ]
                            cy.remove(cy.getElementById(ele.data('id')))
                            cy.add(nodeData);
                        }
                    })
                    cy.add(ledges)
                    var layout = cy.layout({name: 'klay'})
                    layout.run();

                    cy1.add(cy.nodes());
                    cy1.add(cy.edges());
                    cy.remove(cy.nodes())
                    cy.remove(cy.edges())
                    cy.add(allnodes);
                    cy.add(alledges);
                    var layout = cy.layout({
                        name: 'cose-bilkent',
                        animate: false})
                    layout.run();
                    cy1.nodes().on("click", function (a) {
                        if(tippyA!=null){
                            tippyA.hide();
                        }
                        let c = a.target;
                        $('#nodeinfo').text(c.data('id'));
                        var idstr = c.data('id');

                        tippyA = makeTippy(c,c.data('id'));
                        tippyA.show();
                    })
                }
            }else{
                var detailbtn = document.getElementById('labeldetails');
                detailbtn.onclick=function (ev) {
                    alert('idstr');
                    var data = {"parentelement":idstr,"projectid":getCookie("dependency_projectid")};
                    getlal(data);
                }
                var submitbtn = document.getElementById('labelsubmit');
                submitbtn.onclick=function (ev) {
                    alert('idstr');
                    var title = $('#labeltitle').val();
                    var content = $('#labelcontent').val();
                    var projectid = getCookie("dependency_projectid");
                    var parentelement = idstr;
                    var ownername = getCookie("dependency_loginname");
                    if(isnull(ownername)){
                        bootbox.alert('您还没有登陆，不能够保存label！');
                    }else {
                        $.ajax({
                            type: 'POST',
                            url: "/label/savelabel",

                            data:{"title":title,"content":content,"projectid":projectid,"parentelement":parentelement,"ownername":ownername},
                            success: function (data) {
                                if(data.status==1){
                                    bootbox.alert('label保存成功！')
                                    var data = {"parentelement":idstr,"projectid":getCookie("dependency_projectid")};
                                    getlal(data);
                                }else{
                                    bootbox.alert('label保存失败！');
                                }

                            },
                            error: function(data) {
                                console.log(data);
                                bootbox.alert('保存失败！' + data);
                            },
                        })
                    }

                    // tippyA.show();
                }
            }

        })
    })
    var num=0
    $('#reset').click(function () {
        // alert(num);
        $("#contentnavid li").not(":first").remove();
        // !$("#tab-content div").not(":first").remove();
        $('#contentnavid a[href="#cy"]').tab('show'); //显示tab页
        $(" #end").val("");
        $(" #start").val("");
        cy.remove(cy.nodes());
        cy.add(oriNodes);
        cy.add(oriEdges);
        var layout = cy.layout({
            // name: 'klay'

            name: 'euler',
            randomize: true,
            animate: true
        });
        layout.run();
    })

    var cloneObj = function (obj) {
        var newObj = {};
        if (obj instanceof Array) {
            newObj = [];
        }
        for (var key in obj) {
            var val = obj[key];
            //newObj[key] = typeof val === 'object' ? arguments.callee(val) : val; //arguments.callee 在哪一个函数中运行，它就代表哪个函数, 一般用在匿名函数中。
            newObj[key] = typeof val === 'object' ? cloneObj(val): val;
        }
        return newObj;
    };
    //填充树状菜单
    function fillMenu(val){
        console.log(val);
        var mentList = val.split(".");
        for(var i=0;i<mentList.length;i++){
            console.log(mentList[i]);
            if($("#"+mentList[i]).length>0){

            }else{
                if(i==0){
                    // alert(mentList)
                    var parent = $("#menu ul:first-child");
                    var obj=document.createElement("li");
                    obj.setAttribute('id',mentList[i]);
                    obj.innerHTML = "<a href = \"javascript:void(0);\" onclick =\"\">"+mentList[i]+"</a>";
                    if(i!=mentList.length-1){
                        var objul=document.createElement("ul");
                        obj.appendChild(objul);
                    }
                    parent.append(obj)
                }else{
                    var parent = $("#"+mentList[i-1]+" ul:first");
                    i
                    var obj=document.createElement("li");
                    obj.setAttribute('id',mentList[i]);
                    if(i==mentList.length-1){
                        obj.innerHTML = "<a class='lastselect' value=\""+val+"\" href = \"javascript:void(0);\" onclick =\"\">"+mentList[i]+"</a>";
                    }else{
                        obj.innerHTML = "<a href = \"javascript:void(0);\" onclick =\"\">"+mentList[i]+"</a>";
                    }

                    if(i!=mentList.length-1){
                        var objul=document.createElement("ul");
                        obj.appendChild(objul);
                    }
                    parent.append(obj)
                }
            }

        }

    }
    //结点放大显示
    $('a[class="lastselect"]').click(function () {
        var className = $(this).attr("value");
        cy.collection("edge").removeClass('edgeActive');
        cy.collection("node").removeClass('nodeActive');
        cy.nodes('[className="'+className+'"]').addClass('nodeActive');
        cy.nodes('[className="'+className+'"]').neighborhood("edge").addClass('edgeActive');
    })
    $("#inputsearch").click(function(e){
        // alert($("#local2").val())
        let c = cy.getElementById($("#local2").val());
        cy.collection("edge").removeClass('edgeActive');
        cy.collection("node").removeClass('nodeActive');
        c.addClass('nodeActive');
        c.neighborhood("edge").addClass('edgeActive');
    });

    function addlabel(data){
        $('#solutions ul li').remove();
        for (var i=0;i<data.length;i++)
        {
            $('#solutions ul').append("<li style='margin-top: 10px'>\n" +
                "<div class=\"solutit\">\n" +
                "  <h4>"+data[i].ownername+"</h4>\n" +
                "  <p>"+data[i].title+"</p>\n" +
                "  </n><a class=\"aaa\" href=\"javascript:void(0)\">了解详情</a>\n" +
                " </div>\n" +
                "<div class=\"solutit2\">\n" +
                "  <h4>"+data[i].ownername+"</h4>\n" +
                "  <h5>"+data[i].title+"</h5>\n" +
                "  <span></span>\n" +
                "  <p>"+data[i].content+"</p>\n" +
                "  <span></span>\n" +
                "  <p class='time'>"+data[i].createtime+"</p>\n" +
                "</div>\n" +
                "              </li>")
        }

        $('#solutions li').click(function(){

            if($(this).find('.solutit2').height()==0){
                $(this).find('.solutit2').stop().animate({
                    height:'300'
                },600);
            }else{
                $(this).find('.solutit2').stop().animate({
                    height:'0'
                },600);
            }
        });
    }
    var getRandomColor = function() {

        return '#' +
            (function(color) {
                return (color += '0123456789abcdef' [Math.floor(Math.random() * 16)]) && (color.length == 6) ? color : arguments.callee(color);
            })('');
    }
}
function isnull(val) {

    var str = val;
    if (str == '' || str == undefined || str == null) {
        return true;
        // console.log('空')
    } else {
        return false;
        // console.log('非空');
    }
}
