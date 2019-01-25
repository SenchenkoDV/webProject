function changeLocale(Val) {
    location = self.location.href
    var x = new XMLHttpRequest();
    x.open("POST", "/../web?command=locale&locale=" + Val, false);
    x.send();
    location = self.location.href
}
