function changeLocale(Val) {
    var x = new XMLHttpRequest();
    x.open("GET", "/../web?command=locale&locale=" + Val, false);
    x.send(null);
    location = self.location.href
}