val wholeSite = """
    <!DOCTYPE html>
    <html>
    <head>
    <title>Page Title</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <style>
    * {
      box-sizing: border-box;
    }

    /* Style the body */
    body {
      font-family: Arial;
      margin: 0;
    }

    /* Header/logo Title */
    .header {
      padding: 60px;
      text-align: center;
      background: #1abc9c;
      color: white;
    }

    /* Style the top navigation bar */
    .navbar {
      display: flex;
      background-color: #333;
    }

    /* Style the navigation bar links */
    .navbar a {
      color: white;
      padding: 14px 20px;
      text-decoration: none;
      text-align: center;
    }

    /* Change color on hover */
    .navbar a:hover {
      background-color: #ddd;
      color: black;
    }

    /* Column container */
    .row {
      display: flex;
      flex-wrap: wrap;
    }

    /* Create two unequal columns that sits next to each other */
    /* Sidebar/left column */
    .side {
      flex: 30%;
      background-color: #f1f1f1;
      padding: 20px;
    }

    /* Main column */
    .main {
      flex: 70%;
      background-color: white;
      padding: 20px;
    }

    /* Fake image, just for this example */
    .fakeimg {
      background-color: #aaa;
      width: 100%;
      padding: 20px;
    }

    /* Footer */
    .footer {
      padding: 20px;
      text-align: center;
      background: #ddd;
    }

    /* Responsive layout - when the screen is less than 700px wide, make the two columns stack on top of each other instead of next to each other */
    @media (max-width: 700px) {
      .row, .navbar {
        flex-direction: column;
      }
    }
    </style>
    </head>
    <body>

    <!-- Note -->
    <div style="background:yellow;padding:5px">
      <h4 style="text-align:center">Resize the browser window to see the responsive effect.</h4>
    </div>

    <!-- Header -->
    <div class="header">
      <h1>My Website</h1>
      <p>With a <b>flexible</b> layout.</p>
    </div>

    <!-- Navigation Bar -->
    <div class="navbar">
      <a href="#">Link</a>
      <a href="#">Link</a>
      <a href="#">Link</a>
      <a href="#">Link</a>
    </div>

    <!-- The flexible grid (content) -->
    <div class="row">
      <div class="side">
        <h2>About Me</h2>
        <h5>Photo of me:</h5>
        <div class="fakeimg" style="height:200px;">Image</div>
        <p>Some text about me in culpa qui officia deserunt mollit anim..</p>
        <h3>More Text</h3>
        <p>Lorem ipsum dolor sit ame.</p>
        <div class="fakeimg" style="height:60px;">Image</div><br>
        <div class="fakeimg" style="height:60px;">Image</div><br>
        <div class="fakeimg" style="height:60px;">Image</div>
      </div>
      <div class="main">
        <h2>TITLE HEADING</h2>
        <h5>Title description, Dec 7, 2017</h5>
        <div class="fakeimg" style="height:200px;">Image</div>
        <p>Some text..</p>
        <p>Sunt in culpa qui officia deserunt mollit anim id est laborum consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco.</p>
        <br>
        <h2>TITLE HEADING</h2>
        <h5>Title description, Sep 2, 2017</h5>
        <div class="fakeimg" style="height:200px;">Image</div>
        <p>Some text..</p>
        <p>Sunt in culpa qui officia deserunt mollit anim id est laborum consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco.</p>
      </div>
    </div>

    <!-- Footer -->
    <div class="footer">
      <h2>Footer</h2>
    </div>

    </body>
    </html>

""".trimIndent()

val expectedCode = """@Composable
fun GeneratedComposable(){
 document.title="Page Title"
fun appStylesheet() = object : StyleSheet() {
init {
"*" style { 
boxSizing("border-box")
}
"body" style { 
fontFamily("Arial")
property("margin","0")
}
".header" style { 
padding(60.px)
textAlign("center")
property("background","#1abc9c")
property("color","white")
}
".navbar" style { 
display(DisplayStyle.Flex)
property("background-color","#333")
}
".navbar a" style { 
property("color","white")
padding(14.px, 20.px)
textDecoration("none")
textAlign("center")
}
".navbar a:hover" style { 
property("background-color","#ddd")
property("color","black")
}
".row" style { 
display(DisplayStyle.Flex)
flexWrap(FlexWrap.Wrap)
}
".side" style { 
property("flex","30%")
property("background-color","#f1f1f1")
padding(20.px)
}
".main" style { 
property("flex","70%")
property("background-color","white")
padding(20.px)
}
".fakeimg" style { 
property("background-color","#aaa")
width(100.percent)
padding(20.px)
}
".footer" style { 
padding(20.px)
textAlign("center")
property("background","#ddd")
}
media("(max-width:700px)") {
".row, .navbar" style { 
flexDirection(FlexDirection.Column)
}
}
}}
 
Style(appStylesheet())
// Note 
Div (attrs = {
style {
property("background","yellow")
padding(5.px)
}
}){
H4 (attrs = {
style {
textAlign("center")
}
}){
Text("Resize the browser window to see the responsive effect.")

}

}
// Header 
Div (attrs = {
classes("header")
}){
H1 {
Text("My Website")

}
P {
Text("With a ")
B {
Text("flexible")

}
Text(" layout.")

}

}
// Navigation Bar 
Div (attrs = {
classes("navbar")
}){
A (href = "#"){
Text("Link")

}
A (href = "#"){
Text("Link")

}
A (href = "#"){
Text("Link")

}
A (href = "#"){
Text("Link")

}

}
// The flexible grid (content) 
Div (attrs = {
classes("row")
}){
Div (attrs = {
classes("side")
}){
H2 {
Text("About Me")

}
H5 {
Text("Photo of me:")

}
Div (attrs = {
classes("fakeimg")
style {
height(200.px)
}
}){
Text("Image")

}
P {
Text("Some text about me in culpa qui officia deserunt mollit anim..")

}
H3 {
Text("More Text")

}
P {
Text("Lorem ipsum dolor sit ame.")

}
Div (attrs = {
classes("fakeimg")
style {
height(60.px)
}
}){
Text("Image")

}
Br {}
Div (attrs = {
classes("fakeimg")
style {
height(60.px)
}
}){
Text("Image")

}
Br {}
Div (attrs = {
classes("fakeimg")
style {
height(60.px)
}
}){
Text("Image")

}

}
Div (attrs = {
classes("main")
}){
H2 {
Text("TITLE HEADING")

}
H5 {
Text("Title description, Dec 7, 2017")

}
Div (attrs = {
classes("fakeimg")
style {
height(200.px)
}
}){
Text("Image")

}
P {
Text("Some text..")

}
P {
Text("Sunt in culpa qui officia deserunt mollit anim id est laborum consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco.")

}
Br {}
H2 {
Text("TITLE HEADING")

}
H5 {
Text("Title description, Sep 2, 2017")

}
Div (attrs = {
classes("fakeimg")
style {
height(200.px)
}
}){
Text("Image")

}
P {
Text("Some text..")

}
P {
Text("Sunt in culpa qui officia deserunt mollit anim id est laborum consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco.")

}

}

}
// Footer 
Div (attrs = {
classes("footer")
}){
H2 {
Text("Footer")

}

}
}
"""