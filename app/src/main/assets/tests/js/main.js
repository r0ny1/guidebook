const questions = [
    {
        label: '1. О чём идёт речь: Новые сведения о чем-либо (об объектах, процессах, явлениях), полученные при помощи некоторого метода интерпретации данных, считанных с материального носителя',
        responses: [
            {label: 'информация', trusted: true},
            {label: 'интерполяция', trusted: false},
            {label: 'информатика', trusted: false},
            {label: 'инициализация', trusted: false},
        ]
    },
    {
        label: '2. Определение информатики',
        responses: [
            {label: 'применение компьютера в учебном процессе', trusted: false},
            {label: 'междисциплинарное научное направление, изучающее вопросы производства, хранения, накопления, передачи, обработки и использования информации', trusted: true},
            {label: 'расположение информации на технических носителях', trusted: false},
            {label: 'информация, ее хранение и сортировка данных', trusted: false},
        ]
    },
    {
        label: '3. Определение системы счисления',
        responses: [
            {label: 'совокупность всех чисел', trusted: false},
            {label: 'совокупность букв и чисел', trusted: false},
            {label: 'совокупность правил наименования и записи чисел, а также выполнение арифметических операций над ними', trusted: true},
            {label: 'множество всех возможных букв и чисел', trusted: false},
        ]
    },
    {
        label: '4. Что такое разрядная сетка?',
        responses: [
            {label: 'сетка, которая разрядилась, теперь её нужно зарядить', trusted: false},
            {label: 'подмножество двоичных разрядов', trusted: false},
            {label: 'основание числа записывается как его нижний индекс', trusted: false},
            {label: 'множество двоичных разрядов в памяти компьютера для хранения и обработки чисел', trusted: true},
        ]
    },
    {
        label: '5. Что такое машинный ноль?',
        responses: [
            {label: 'число, которое машина воспринимает как ноль', trusted: true},
            {label: 'ноль, который превратился в машину', trusted: false},
            {label: 'машина, которая превратилась в ноль', trusted: false},
            {label: 'это обыкновенный ноль', trusted: false},
        ]
    },
    {
        label: '6. Что такое комбинационная схема?',
        responses: [
            {label: 'устройство, которое делает комбинации', trusted: false},
            {label: 'устройство, сигнал, на выходе которого в данный момент времени зависит только от сигналов на входе', trusted: true},
            {label: 'схема комбинаций машинных команд', trusted: false},
            {label: 'устройство, сигнал на выходе которого в данный момент времени зависит от состояния в прошлый момент времени и новых входных данных', trusted: false},
        ]
    },
    {
        label: '7. Что такое таблица истинности?',
        responses: [
            {label: 'таблица, имеющая в себе только истинные утверждения', trusted: false},
            {label: 'таблица, говорящая только правду', trusted: false},
            {label: 'таблица, отображающая все возможные значения аргументов данной функции и её значение при каждом из значений данных аргументов', trusted: true},
            {label: 'таблица, имеющая в себе только ложные утверждения', trusted: false},
        ]
    },
    {
        label: '8. Что такое мультиплексор?',
        responses: [
            {label: 'комбинационная схема, обладающая 2^n  адресными входами и n информационными входами (возможно присутствие разрешающего входа) и одним выходом. (двоичное число, поданное на адресные входы, определит номер информационного входа, который будет подключен к выходу)', trusted: false},
            {label: 'комбинационная схема , которая имеет 2^n входов и n выходов. Выполняет преобразование унитарного (все кроме одного бита либо ноль , либо один) двоичного кода в двоичный код', trusted: false},
            {label: 'шифратор, который формирует весь доступный по разрядности диапазон двоичных чисел', trusted: false},
            {label: 'комбинационная схема, обладающая n адресными входами и 2^n информационными входами (возможно присутствие разрешающего входа) и одним выходом. (двоичное число, поданное на адресные входы, определит номер информационного входа, который будет подключен к выходу)', trusted: true},
        ]
    },
    {
        label: '9. Что такое триггер?',
        responses: [
            {label: 'простейший цифровой автомат, состояние которого в текущий момент времени зависит от комбинации входных сигналов и его состояния в прошлый момент времени. Может хранить 1 бит информации, является простейшим запоминающим устройством', trusted: true},
            {label: 'это сериал такой на первом канале был', trusted: false},
            {label: 'это спусковой крючок', trusted: false},
            {label: 'цифровое устройство, предназначенное для сложения чисел(при использовании специального кода можно осуществлять и вычитание)', trusted: false},
        ]
    },
    {
        label: '10.	Что такое счётчик?',
        responses: [
            {label: 'цифровой автомат, строящийся на основе триггеров , для записи , хранения и выдачи информации. Этот минимум операций может выполнить простейший регистр – регистр хранения', trusted: false},
            {label: 'цифровой автомат, построенный на триггерах различных типов, и предназначенный для подсчёта количества поступивших импульсов', trusted: true},
            {label: 'простейший цифровой автомат, состояние которого в текущий момент времени зависит от комбинации входных сигналов и его состояния в прошлый момент времени', trusted: false},
            {label: 'комбинационная схема сравнивающая числа', trusted: false},
        ]
    },
]
let responses = [];

document.addEventListener('DOMContentLoaded', function () {
    let wrap = document.getElementById('wrap')
    questions.forEach((question, index1) => {
        let ul = document.createElement('div')
        let span = document.createElement('span');
        span.textContent = question.label;
        ul.appendChild(span)

        question.responses.forEach((response, index2) => {
            let li = document.createElement('div');
            let input = document.createElement('input');
            let label = document.createElement('label');
            let span = document.createElement('span');
            span.textContent = response.label;

            input.type = 'radio';
            input.name = `quest-${index1}`;
            input.value = index2.toString();
            label.id = `${index1}${index2}`

            input.addEventListener('change', (event) => {
                let i = responses.findIndex(item => item.quest === index1)
                if (i === -1)
                    responses.push({
                        value: response.trusted,
                        resp: index2,
                        quest: index1
                    })
                else responses[i] = {
                    value: response.trusted,
                    resp: index2,
                    quest: index1
                }
            })

            label.appendChild(input)
            label.appendChild(span)
            li.appendChild(label);
            ul.appendChild(li);
        })

        wrap.appendChild(ul)
    })
})

let onClick = () => {
    let len = questions.length;
    let cnt = 0;
    console.log(responses)

    responses.forEach(item=>{
        if (item.value)
            cnt++
        else {
            document.getElementById(`${item.quest}${item.resp}`).style.color = 'red'
        }
    })

    /*Object.keys(responses).forEach(key=>{
        if (responses[key])
            cnt++;
    })*/
     alert( `Верных ответов: ${cnt}`);
}
