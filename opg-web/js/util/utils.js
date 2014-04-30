Object.size = function(obj)
{
    var size = 0, key

    for (key in obj)
    {
        if (obj.hasOwnProperty(key))
        	size++
    }

    return size
}

d3.selection.prototype.moveToFront = function()
{
	return this.each(function()
	{
		this.parentNode.appendChild(this);
	});
};

d3.selection.prototype.size = function()
{
    var n = 0;
    this.each(function() { ++n; });
    return n;
};

if (typeof String.prototype.startsWith != 'function')
{
	String.prototype.startsWith = function(str)
	{
		return this.slice(0, str.length) == str
	}
}

if (typeof String.prototype.endsWith != 'function')
{
	String.prototype.endsWith = function(str)
	{
		return this.slice(-str.length) == str
	}
}

if (typeof String.prototype.contains != 'function')
{
	String.prototype.contains = function(str)
	{
		return this.indexOf(str) !== -1
	}
}

var utils = {}

utils.getEchoFunction = function(bool)
{
	return function()
	{
		console.log('returning', bool)
		return bool
	}
}

utils.getD3CancelFunction = function()
{
	return function()
	{
		if ('event' in d3 && typeof d3.event.stopPropagation === 'function')
			d3.event.stopPropagation()
	}
}

utils.arrayToDict = function(array, key)
{
	var obj = {}

	for (var i in array)
		obj[array[i][key]] = array[i]

	return obj
}

utils.getSpousePath = function(path)
{
	var ahn = parseInt('1' + path.substring(1), 2)

	if (ahn == 1)
		return undefined
	else if (ahn % 2 == 0)
	{
		var sp = (ahn + 1).toString(2)
		return '.' + sp.substring(1)
	}
	else
	{
		var np = (ahn - 1).toString(2)
		return '.' + np.substring(1)
	}
}

utils.arrayRemove = function(arr)
{
    var what, a = arguments, L = a.length, ax;
    while (L > 1 && arr.length) {
        what = a[--L];
        while ((ax= arr.indexOf(what)) !== -1) {
            arr.splice(ax, 1);
        }
    }
    return arr;
}

utils.getWindowScalar = function()
{
    var w = $(window).width()

    if (w >= 1600)
        return 1
    else if (w < 800)
        return 0.5
    else if (w < 1000)
        return 0.65
    else if (w < 1200)
        return 0.8
    else
        return 0.9
}

utils.generateRandomPerson = function(path)
{
	var givenname = utils.generateRandomName(g, false)
	var surname = '';

	// get child data
	var cp = path.substring(0, path.length - 1)

	if (g === 'M' && cp in data.people)
		// if the person is male, copy his child's last name
		surname = data.people[cp].surname
	else
		// otherwise just generate one
		surname = utils.generateRandomSurname()

	var p = new Person(path, g, false, givenname, surname)
	data.people[path] = p
	return p
}

utils.generateRandomName = function(gender, surname)
{
	var name = ''
	if (gender === 'F')
		name = data.randomFemaleNames[Math.floor(Math.random() * data.randomFemaleNames.length)]
	else
		name = data.randomMaleNames[Math.floor(Math.random() * data.randomMaleNames.length)]

	if (surname !== false)
		name += ' ' + (surname || utils.generateRandomSurname())

	return name
}

utils.generateRandomSurname = function()
{
	return data.randomSurnames[Math.floor(Math.random() * data.randomSurnames.length)]
}

utils.randomMaleNames = [
	"Reginald",
    "Ulysses",
    "Gerry",
    "Raul",
    "Billie",
    "Neil",
    "Jaime",
    "Jacques",
    "Randal",
    "Jay",
    "Lorenzo",
    "Jeff",
    "Jospeh",
    "Abdul",
    "Royce",
    "Jan",
    "Raymond",
    "Edward",
    "Nathanael",
    "Damian",
    "Orlando",
    "Rusty",
    "Blair",
    "Roger",
    "Nathaniel",
    "Stefan",
    "Aron",
    "Lyle",
    "Dion",
    "Edwardo",
    "Cristobal",
    "Hubert",
    "Dan",
    "Warner",
    "Merle",
    "Pat",
    "Rex",
    "Amos",
    "Eli",
    "Oliver",
    "Kristofer",
    "Johnie",
    "Avery",
    "Winston",
    "Cole",
    "Milo",
    "Wilber",
    "Antonio",
    "Dean",
    "Ted"
]

utils.randomFemaleNames = [
	"Azalee",
    "Elenore",
    "Julianne",
    "Danyelle",
    "Ciara",
    "Lawanda",
    "Polly",
    "Kaci",
    "Erika",
    "Carylon",
    "Margarite",
    "Suanne",
    "Gina",
    "Vanetta",
    "Yen",
    "Xiao",
    "Zula",
    "Darci",
    "See",
    "Marhta",
    "Mamie",
    "Dori",
    "Jessi",
    "Lue",
    "Elizabet",
    "Wonda",
    "Regan",
    "Beatris",
    "Demetria",
    "Elena",
    "Hildegarde",
    "Elaina",
    "Nerissa",
    "Jada",
    "Clementina",
    "Marcy",
    "Lindy",
    "Deane",
    "Vernice",
    "Anisha",
    "Kathe",
    "Hope",
    "Stephani",
    "Inocencia",
    "Lea",
    "Rosalie",
    "Beckie",
    "Pa",
    "Bonny",
    "Li"
]

utils.randomSurnames = [
	"Sia",
    "Sylvestre",
    "Tarter",
    "Lamantia",
    "Ugarte",
    "Iler",
    "Going",
    "Bettinger",
    "Fein",
    "Linwood",
    "Eppler",
    "Scarborough",
    "Vanzandt",
    "Pecor",
    "Pendergrass",
    "Leiter",
    "Atencio",
    "Magar",
    "Vanderhorst",
    "Tineo",
    "Fuhr",
    "Lindell",
    "Givan",
    "Stransky",
    "Duhaime",
    "Mccoy",
    "Lunceford",
    "Hoppes",
    "Siller",
    "Bashore",
    "Dials",
    "Spicer",
    "Penney",
    "Neidig",
    "Fulbright",
    "Dimery",
    "Golding",
    "Lundeen",
    "Kind",
    "Hennen",
    "Tabor",
    "Kostka",
    "Ruffin",
    "Surita",
    "Sylvain",
    "Mcfaul",
    "Latch",
    "Sparr",
    "Dickie",
    "Suzuki"
]