import Entity.*;
import Service.*;
import org.joda.time.DateTime;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws UnsupportedEncodingException {
        try {
            Scanner scanner = new Scanner(System.in);
            ArrayList<Client> clients = new ArrayList<>();
            ArrayList<Category> categories = new ArrayList<>();
            ArrayList<PaymentMethod> paymentMethods = new ArrayList<>();
            ArrayList<ConfirmationKey> confirmationKeys = new ArrayList<>();
            ProductService productService = new ProductService();
            while(true) {
            /*ThreadConnectedClient.sendConfirmationEmail(new Client(1, "123", "", "rsma1999@gmail.com", "", Client.SEX.мужской,
                    new LocalDate(), new DateTime()));*/
                menu();
                int com = scanner.nextInt();
                switch (com) {
                    case 1:
                        Server server = new Server();
                        server.start();
                        break;
                    case 2:
                        ClientService clientService = new ClientService();
                        clients = (ArrayList<Client>) clientService.getAllClients();
                        CategoryService categoryService = new CategoryService();
                        categories = (ArrayList<Category>) categoryService.getAllCategories();
                        PaymentMethodService paymentMethodService = new PaymentMethodService();
                        paymentMethods = (ArrayList<PaymentMethod>) paymentMethodService.getAllPaymentMethods();
                        ConfirmationKeyService confirmationKeyService = new ConfirmationKeyService();
                        confirmationKeys = (ArrayList<ConfirmationKey>) confirmationKeyService.getAllConfirmationKeys();
                        break;
                    case 3:
                        ClientService service = new ClientService();
                        for (int i = 0; i < clients.size(); i++)
                            service.addClient(Client.getClientFromCP1251ToUTF8Charset(clients.get(i)));
                        CategoryService service1 = new CategoryService();
                        for (int i = 0; i < categories.size(); i++)
                            service1.addCategory(Category.getCategoryFromCP1251ToUTF8Charset(categories.get(i)));
                        PaymentMethodService methodService = new PaymentMethodService();
                        for (int i = 0; i < paymentMethods.size(); i++)
                            methodService.addPaymentMethod(PaymentMethod.getPaymentMethodFromCP1251ToUTF8Charset(paymentMethods.get(i)));
                        ConfirmationKeyService keyService = new ConfirmationKeyService();
                        for (int i = 0; i < confirmationKeys.size(); i++)
                            keyService.addConfirmationKey(confirmationKeys.get(i));
                        break;
                    case 4:
                        ArrayList<Product> products = (ArrayList<Product>) productService.getAllProducts();
                        for (Product p : products) {
                            JSONObject jsonObject = new JSONObject(p.getSpecification());
                            switch ((int)p.getId()){
                                /*case 1:
                                    JSONObject js1 = new JSONObject();
                                    js1.put("Ядро", "Kabini");
                                    js1.put("Техпроцесс", "28 нм");
                                    js1.put("Количество ядер", "2");
                                    js1.put("Максимальное число потоков", "2 шт");
                                    js1.put("Кэш L1 (инструкции)", "64 КБ");
                                    js1.put("Кэш L1 (данные)", "64 КБ");
                                    js1.put("Объем кэша L2", "1 МБ");
                                    js1.put("Объем кэша L3", "нет");
                                    jsonObject.put("Ядро и архитектура", js1);
                                    JSONObject js2 = new JSONObject();
                                    js2.put("Базовая частота процессора (МГц)", "1450 МГц");
                                    js2.put("Максимальная частота в турбо режиме (МГц)", "нет");
                                    js2.put("Множитель", "14.5");
                                    js2.put("Свободный множитель", "нет");
                                    jsonObject.put("Частота и возможность разгона", js2);
                                    JSONObject js3 = new JSONObject();
                                    js3.put("Тип памяти", "DDR3");
                                    js3.put("Максимально поддерживаемый объем памяти", "32 ГБ");
                                    js3.put("Количество каналов", "1");
                                    js3.put("Минимальная частота оперативной памяти", "800 МГц");
                                    js3.put("Максимальная частота оперативной памяти", "1333 МГц");
                                    js3.put("Поддержка режима ECC", "есть");
                                    jsonObject.put("Параметры оперативной памяти", js3);
                                    JSONObject js4 = new JSONObject();
                                    js4.put("Тепловыделение (TDP)", "25 Вт");
                                    js4.put("Максимальная температура процессора", "90 °C");
                                    jsonObject.put("Тепловые характеристики", js4);
                                    JSONObject js5 = new JSONObject();
                                    js5.put("Интегрированное графическое ядро", "есть");
                                    js5.put("Модель графического процессора", "Radeon HD 8240");
                                    js5.put("Максимальная частота графического ядра", "400 МГц");
                                    jsonObject.put("Графическое ядро", js5);
                                    JSONObject js6 = new JSONObject();
                                    js6.put("Пропускная способность шины", "5 GT/s");
                                    js6.put("Встроенный контроллер PCI Express", "PCI-E 2.0");
                                    js6.put("Число линий PCI Express", "4 шт");
                                    jsonObject.put("Шина и контроллеры", js6);
                                    JSONObject js7 = new JSONObject();
                                    js7.put("Поддержка 64-битного набора команд", "AMD64");
                                    js7.put("Многопоточность", "нет");
                                    js7.put("Технология виртуализации", "есть");
                                    js7.put("Технология повышения частоты процессора", "нет");
                                    js7.put("Технология энергосбережения", "PowerNow!");
                                    js7.put("Набор инструкций и команд", "SSE4.2, MMX, SSE, SSE2, SSE3, SSSE3, SSE4, SSE4.1, AES, AVX, BMI1, F16C, EVP");
                                    jsonObject.put("Команды, инструкции, технологии", js7);
                                    p.setSpecification(jsonObject.toString());
                                    productService.updateProduct(p);
                                    break;*/
                                /*case 2:
                                    JSONObject js1_2 = new JSONObject();
                                    js1_2.put("Ядро", "Kaveri");
                                    js1_2.put("Техпроцесс", "28 нм");
                                    js1_2.put("Количество ядер", "4");
                                    js1_2.put("Максимальное число потоков", "4 шт");
                                    js1_2.put("Кэш L1 (инструкции)", "192 КБ");
                                    js1_2.put("Кэш L1 (данные)", "64 КБ");
                                    js1_2.put("Объем кэша L2", "4 МБ");
                                    js1_2.put("Объем кэша L3", "нет");
                                    jsonObject.put("Ядро и архитектура", js1_2);
                                    JSONObject js2_2 = new JSONObject();
                                    js2_2.put("Базовая частота процессора (МГц)", "3100 МГц");
                                    js2_2.put("Максимальная частота в турбо режиме (МГц)", "3800 МГц");
                                    js2_2.put("Множитель", "31");
                                    js2_2.put("Свободный множитель", "нет");
                                    jsonObject.put("Частота и возможность разгона", js2_2);
                                    JSONObject js3_2 = new JSONObject();
                                    js3_2.put("Тип памяти", "DDR3");
                                    js3_2.put("Максимально поддерживаемый объем памяти", "64 ГБ");
                                    js3_2.put("Количество каналов", "2");
                                    js3_2.put("Минимальная частота оперативной памяти", "800 МГц");
                                    js3_2.put("Максимальная частота оперативной памяти", "2133 МГц");
                                    js3_2.put("Поддержка режима ECC", "нет");
                                    jsonObject.put("Параметры оперативной памяти", js3_2);
                                    JSONObject js4_2 = new JSONObject();
                                    js4_2.put("Тепловыделение (TDP)", "65 Вт");
                                    js4_2.put("Максимальная температура процессора", "71 °C");
                                    jsonObject.put("Тепловые характеристики", js4_2);
                                    JSONObject js5_2 = new JSONObject();
                                    js5_2.put("Интегрированное графическое ядро", "нет");
                                    js5_2.put("Модель графического процессора", "нет");
                                    jsonObject.put("Графическое ядро", js5_2);
                                    JSONObject js6_2 = new JSONObject();
                                    js6_2.put("Системная шина", "QPI");
                                    js6_2.put("Пропускная способность шины", "5 GT/s");
                                    js6_2.put("Встроенный контроллер PCI Express", "PCI-E 3.0");
                                    js6_2.put("Число линий PCI Express", "16 шт");
                                    jsonObject.put("Шина и контроллеры", js6_2);
                                    JSONObject js7_2 = new JSONObject();
                                    js7_2.put("Поддержка 64-битного набора команд", "AMD64");
                                    js7_2.put("Многопоточность", "нет");
                                    js7_2.put("Технология виртуализации", "есть");
                                    js7_2.put("Технология повышения частоты процессора", "Turbo Core 3.0");
                                    js7_2.put("Технология энергосбережения", "PowerNow!");
                                    js7_2.put("Набор инструкций и команд", "BMI1, MMX, SSE, SSE2, SSE3, SSSE3, SSE4, SSE4.1, SSE4.2, AES, AVX, F16C, FMA3, FMA4, TBM, EVP, XOP, SSE4a");
                                    jsonObject.put("Команды, инструкции, технологии", js7_2);
                                    p.setSpecification(jsonObject.toString());
                                    productService.updateProduct(p);
                                    break;*/
                                /*case 3:
                                    JSONObject js1 = new JSONObject();
                                    js1.put("Линейка графических процессоров", "GeForce");
                                    js1.put("Графический процессор", "GeForce GT 710");
                                    js1.put("Поддержка стандартов", "DirectX 12, OpenGL 4.4");
                                    jsonObject.put("Основные параметры", js1);
                                    JSONObject js2 = new JSONObject();
                                    js2.put("Количество видеочипов", "1");
                                    js2.put("Технологический процесс", "28 нм");
                                    js2.put("Штатная частота работы видеочипа (МГц)", "954 МГц");
                                    js2.put("Турбочастота", "нет");
                                    js2.put("Количество универсальных процессоров", "192");
                                    js2.put("Число текстурных блоков", "16");
                                    js2.put("Число блоков растеризации", "8");
                                    js2.put("Версия шейдеров", "5.0");
                                    js2.put("Максимальная температура процессора", "95°");
                                    js2.put("Поддержка трассировки лучей", "нет");
                                    jsonObject.put("Спецификации видеопроцессора", js2);
                                    JSONObject js3 = new JSONObject();
                                    js3.put("Объем видеопамяти", "1 ГБ");
                                    js3.put("Тип памяти", "GDDR3");
                                    js3.put("Эффективная частота памяти (МГц)", "1600 МГц");
                                    js3.put("Разрядность шины памяти", "64 бит");
                                    js3.put("Максимальная пропускная способность памяти (Гбайт/сек)", "12.8 Гбайт/сек");
                                    jsonObject.put("Спецификации видеопамяти", js3);
                                    JSONObject js4 = new JSONObject();
                                    js4.put("Интерфейс подключения", "PCI-E");
                                    js4.put("Максимальная температура процессора", "2.0");
                                    js4.put("Поддержка мультипроцессорной конфигурации", "не поддерживается");
                                    jsonObject.put("Подключение", js4);
                                    JSONObject js5 = new JSONObject();
                                    js5.put("Количество подключаемых одновременно мониторов", "3 шт");
                                    js5.put("Видео разъемы", "DVI-D, HDMI, VGA (D-Sub)");
                                    js5.put("Максимальное разрешение", "4096x2160");
                                    jsonObject.put("Вывод изображения", js5);
                                    JSONObject js6 = new JSONObject();
                                    js6.put("Необходимость дополнительного питания", "нет");
                                    js6.put("Разъемы дополнительного питания", "нет");
                                    js6.put("Максимальное энергопотребление", "19 Вт");
                                    js6.put("Рекомендуемый блок питания", "300 Вт");
                                    jsonObject.put("Питание", js6);
                                    JSONObject js7 = new JSONObject();
                                    js7.put("Тип охлаждения", "пассивное");
                                    js7.put("Тип и количество установленных вентиляторов", "нет");
                                    jsonObject.put("Система охлаждения", js7);
                                    JSONObject js8 = new JSONObject();
                                    js8.put("Низкопрофильная карта (Low Profile)", "есть");
                                    js8.put("Количество занимаемых слотов расширения", "2");
                                    js8.put("Длина видеокарты", "140 мм");
                                    jsonObject.put("Габариты", js8);
                                    JSONObject js9 = new JSONObject();
                                    js9.put("Комплектация", "диск с ПО, документация");
                                    jsonObject.put("Дополнительно", js9);
                                    p.setSpecification(jsonObject.toString());
                                    productService.updateProduct(p);
                                    break;*/
                                /*case 4:
                                    JSONObject js1 = new JSONObject();
                                    js1.put("Линейка графических процессоров", "GeForce");
                                    js1.put("Графический процессор", "GeForce GT 710");
                                    js1.put("Поддержка стандартов", "OpenGL 4.5, DirectX 12");
                                    jsonObject.put("Основные параметры", js1);
                                    JSONObject js2 = new JSONObject();
                                    js2.put("Количество видеочипов", "1");
                                    js2.put("Технологический процесс", "28 нм");
                                    js2.put("Штатная частота работы видеочипа (МГц)", "954 МГц");
                                    js2.put("Турбочастота", "нет");
                                    js2.put("Количество универсальных процессоров", "192");
                                    js2.put("Число текстурных блоков", "16");
                                    js2.put("Число блоков растеризации", "8");
                                    js2.put("Версия шейдеров", "4.0");
                                    js2.put("Максимальная температура процессора", "95°");
                                    js2.put("Поддержка трассировки лучей", "нет");
                                    jsonObject.put("Спецификации видеопроцессора", js2);
                                    JSONObject js3 = new JSONObject();
                                    js3.put("Объем видеопамяти", "1 ГБ");
                                    js3.put("Тип памяти", "GDDR5");
                                    js3.put("Эффективная частота памяти (МГц)", "5012 МГц");
                                    js3.put("Разрядность шины памяти", "32 бит");
                                    js3.put("Максимальная пропускная способность памяти (Гбайт/сек)", "21.9 Гбайт/сек");
                                    jsonObject.put("Спецификации видеопамяти", js3);
                                    JSONObject js4 = new JSONObject();
                                    js4.put("Интерфейс подключения", "PCI-E");
                                    js4.put("Максимальная температура процессора", "2.0");
                                    js4.put("Поддержка мультипроцессорной конфигурации", "не поддерживается");
                                    jsonObject.put("Подключение", js4);
                                    JSONObject js5 = new JSONObject();
                                    js5.put("Количество подключаемых одновременно мониторов", "3 шт");
                                    js5.put("Видео разъемы", "DVI-D, HDMI, VGA (D-Sub)");
                                    js5.put("Максимальное разрешение", "2560x1600");
                                    jsonObject.put("Вывод изображения", js5);
                                    JSONObject js6 = new JSONObject();
                                    js6.put("Необходимость дополнительного питания", "нет");
                                    js6.put("Разъемы дополнительного питания", "нет");
                                    js6.put("Максимальное энергопотребление", "19 Вт");
                                    js6.put("Рекомендуемый блок питания", "300 Вт");
                                    jsonObject.put("Питание", js6);
                                    JSONObject js7 = new JSONObject();
                                    js7.put("Тип охлаждения", "пассивное");
                                    js7.put("Тип и количество установленных вентиляторов", "нет");
                                    jsonObject.put("Система охлаждения", js7);
                                    JSONObject js8 = new JSONObject();
                                    js8.put("Низкопрофильная карта (Low Profile)", "есть");
                                    js8.put("Количество занимаемых слотов расширения", "1");
                                    js8.put("Длина видеокарты", "167 мм");
                                    js8.put("Толщина видеокарты", "17 мм");
                                    jsonObject.put("Габариты", js8);
                                    JSONObject js9 = new JSONObject();
                                    js9.put("Комплектация", "диск с ПО, документация");
                                    jsonObject.put("Дополнительно", js9);
                                    p.setSpecification(jsonObject.toString());
                                    productService.updateProduct(p);
                                    break;
                                case 5:
                                    JSONObject js1_2 = new JSONObject();
                                    js1_2.put("Линейка графических процессоров", "Radeon");
                                    js1_2.put("Графический процессор", "Radeon VII");
                                    js1_2.put("Поддержка стандартов", "OpenGL 4.5, DirectX 12");
                                    jsonObject.put("Основные параметры", js1_2);
                                    JSONObject js2_2 = new JSONObject();
                                    js2_2.put("Количество видеочипов", "1");
                                    js2_2.put("Технологический процесс", "7 нм");
                                    js2_2.put("Штатная частота работы видеочипа (МГц)", "1400 МГц");
                                    js2_2.put("Турбочастота", "1750 МГц");
                                    js2_2.put("Количество универсальных процессоров", "3840");
                                    js2_2.put("Число текстурных блоков", "240");
                                    js2_2.put("Число блоков растеризации", "64");
                                    js2_2.put("Версия шейдеров", "6.1");
                                    js2_2.put("Максимальная температура процессора", "110°");
                                    js2_2.put("Поддержка трассировки лучей", "нет");
                                    jsonObject.put("Спецификации видеопроцессора", js2_2);
                                    JSONObject js3_2 = new JSONObject();
                                    js3_2.put("Объем видеопамяти", "16 ГБ");
                                    js3_2.put("Тип памяти", "HBM2");
                                    js3_2.put("Эффективная частота памяти (МГц)", "2000 МГц");
                                    js3_2.put("Разрядность шины памяти", "4096 бит");
                                    js3_2.put("Максимальная пропускная способность памяти (Гбайт/сек)", "1024 Гбайт/сек");
                                    jsonObject.put("Спецификации видеопамяти", js3_2);
                                    JSONObject js4_2 = new JSONObject();
                                    js4_2.put("Интерфейс подключения", "PCI-E");
                                    js4_2.put("Максимальная температура процессора", "3.0");
                                    js4_2.put("Поддержка мультипроцессорной конфигурации", "CrossFire X");
                                    jsonObject.put("Подключение", js4_2);
                                    JSONObject js5_2 = new JSONObject();
                                    js5_2.put("Количество подключаемых одновременно мониторов", "4 шт");
                                    js5_2.put("Видео разъемы", "HDMI, DisplayPort (3 шт)");
                                    js5_2.put("Максимальное разрешение", "7680x4320");
                                    jsonObject.put("Вывод изображения", js5_2);
                                    JSONObject js6_2 = new JSONObject();
                                    js6_2.put("Необходимость дополнительного питания", "есть");
                                    js6_2.put("Разъемы дополнительного питания", "8-pin + 8-pin");
                                    js6_2.put("Максимальное энергопотребление", "300 Вт");
                                    js6_2.put("Рекомендуемый блок питания", "650 Вт");
                                    jsonObject.put("Питание", js6_2);
                                    JSONObject js7_2 = new JSONObject();
                                    js7_2.put("Тип охлаждения", "активное воздушное");
                                    js7_2.put("Тип и количество установленных вентиляторов", "3 осевых");
                                    jsonObject.put("Система охлаждения", js7_2);
                                    JSONObject js8_2 = new JSONObject();
                                    js8_2.put("Низкопрофильная карта (Low Profile)", "нет");
                                    js8_2.put("Количество занимаемых слотов расширения", "2");
                                    js8_2.put("Длина видеокарты", "280 мм");
                                    js8_2.put("Толщина видеокарты", "40 мм");
                                    jsonObject.put("Габариты", js8_2);
                                    JSONObject js9_2 = new JSONObject();
                                    js9_2.put("Комплектация", "документация");
                                    jsonObject.put("Дополнительно", js9_2);
                                    p.setSpecification(jsonObject.toString());
                                    productService.updateProduct(p);
                                    break;*/
                            }

                        }


                        break;
                    case 5:
                        /*JSONObject jsonObject = new JSONObject();
                        JSONObject js1 = new JSONObject();
                        js1.put("Модель", "GeForce");
                        js1.put("Основной цвет", "Powerman ES722BK");
                        js1.put("Дополнительные цвета", "серебристый");
                        js1.put("Год релиза", "2017");
                        jsonObject.put("Общие параметры", js1);
                        JSONObject js2 = new JSONObject();
                        js2.put("Форм-фактор совместимых плат", "Micro-ATX, Mini-ITX");
                        js2.put("Типоразмер корпуса", "Midi-Tower");
                        js2.put("Корпус для HTPC", "нет");
                        jsonObject.put("Форм-фактор и габариты", js2);
                        JSONObject js3 = new JSONObject();
                        js3.put("Встроенный БП", "нет");
                        js3.put("Размещение блока питания", "верхнее");
                        js3.put("Форм-фактор совместимых блоков питания", "ATX");
                        jsonObject.put("Блок питания", js3);
                        JSONObject js4 = new JSONObject();
                        js4.put("Док-станция для HDD/SSD", "нет");
                        js4.put("Материал корпуса", "сталь, пластик");
                        js4.put("Положение корзин накопителей", "вдоль корпуса");
                        js4.put("Отсеки для 2.5\" накопителей", "нет");
                        js4.put("Количество отсеков 2.5\" накопителей", "нет");
                        js4.put("Число внутренних отсеков 3.5\"", "2 шт");
                        js4.put("Число внешних отсеков 3.5\"", "1 шт");
                        js4.put("Число отсеков 5.25\"", "2 шт");
                        js4.put("Количество слотов расширения", "4");
                        js4.put("Максимальная длина устанавливаемой видеокарты, мм", "350 мм");
                        js4.put("Максимальная высота процессорного кулера, мм", "145 мм");
                        js4.put("Малошумные и антивибрационные корпус", "нет");
                        jsonObject.put("Конструкция", js4);
                        JSONObject js5 = new JSONObject();
                        js5.put("Вентиляторы в комплекте", "нет");
                        js5.put("Поддержка фронтальных вентиляторов", "1 x 90 мм");
                        js5.put("Поддержка тыловых вентиляторов", "1 x 90 мм");
                        js5.put("Поддержка верхних вентиляторов", "нет");
                        js5.put("Поддержка нижних вентиляторов", "нет");
                        js5.put("Поддержка боковых вентиляторов", "нет");
                        js5.put("Возможность установки системы жидкостного охлаждения", "нет");
                        js5.put("Блок управления вентиляторами", "нет");
                        jsonObject.put("Охлаждение", js5);
                        JSONObject js6 = new JSONObject();
                        js6.put("Количество и тип USB портов", "2x USB 2.0");
                        js6.put("Аудио разъемы", "микрофонный вход (jack 3.5), выход на наушники (jack 3.5)");
                        js6.put("Интерфейсы eSATA", "нет");
                        js6.put("Встроенный карт-ридер", "нет");
                        jsonObject.put("Разъемы и интерфейсы лицевой панели", js6);
                        JSONObject js7 = new JSONObject();
                        js7.put("Безвинтовое крепление в отсеках 3,5\"", "нет");
                        js7.put("Безвинтовое крепление в отсеках 5,25\"", "нет");
                        js7.put("Безвинтовое крепление в слотах расширения", "нет");
                        js7.put("Прокладка кабелей за задней стенкой", "нет");
                        js7.put("Вырез в районе крепления кулера CPU", "нет");
                        jsonObject.put("Удобство сборки", js7);
                        JSONObject js8 = new JSONObject();
                        js8.put("Наличие окна на боковой стенке", "нет");
                        js8.put("Подсветка", "нет");
                        js8.put("Пылевой фильтр", "нет");
                        jsonObject.put("Моддинг", js8);
                        JSONObject js9 = new JSONObject();
                        js9.put("Комплектация", "документация");
                        js9.put("Возможность закрывать крышку на замок", "ест");
                        jsonObject.put("Дополнительная информация", js9);
                        JSONObject js10 = new JSONObject();
                        js10.put("Длина", "380 мм");
                        js10.put("Ширина (мм)", "173 мм");
                        js10.put("Высота", "368 мм");
                        js10.put("Вес (кг)", "4.02 кг");
                        jsonObject.put("Габариты, вес", js10);
                        Product p1 = new Product(13, "Корпус Powerman ES722BK черный", "Функциональный корпус Powerman ES722BK в черном цвете соответствует стандарту Midi-Tower, что позволяет собрать достойный аппарат. Модель предусматривает поддержку плат Micro-ATX и Mini-ITX и видеокарт длиной 350 мм. Высота поддерживаемого процессорного кулера составляет 145 мм. Вдоль корпуса расположены внутренние отсеки 3.5\", отсек для накопителя 2.5\", а также два отсека 5.25\".\n" +
                                "Powerman ES722BK предусматривает четыре слота расширения. Система охлаждения корпуса представлена одним вентилятором 90 мм. Корпус оснащен разъемом USB 2.0, микрофонным входом и выходом на наушники jack 3.5. Интерес представляет возможность закрывать крышку на замок.",
                                2150, 30, jsonObject.toString(), new DateTime());
                        productService.addProduct(p1);*/
                        JSONObject jsonObject = new JSONObject();
                        JSONObject js1 = new JSONObject();

                        js1.put("Модель", "ASRock B365M PHANTOM GAMING 4");
                        js1.put("Год релиза", "2019");
                        jsonObject.put("Общие параметры", js1);
                        JSONObject js2 = new JSONObject();
                        js2.put("Форм-фактор", "Micro-ATX");
                        js2.put("Высота", "244 мм");
                        js2.put("Ширина (мм)", "242 мм");
                        jsonObject.put("Форм-фактор и размеры", js2);
                        JSONObject js3 = new JSONObject();
                        js3.put("Сокет", "LGA 1151-v2");
                        js3.put("Встроенный центральный процессор", "нет");
                        jsonObject.put("Процессор", js3);
                        JSONObject js4 = new JSONObject();
                        js4.put("Чипсет", "Intel B365");
                        js4.put("BIOS", "AMI");
                        js4.put("UEFI", "есть");
                        js4.put("Поддержка SLI/CrossFire", "CrossFire X");
                        jsonObject.put("Чипсет", js4);
                        JSONObject js5 = new JSONObject();
                        js5.put("Форм фактор поддерживаемой памяти", "DIMM");
                        js5.put("Тип поддерживаемой памяти", "DDR4");
                        js5.put("Поддержка режима ECC", "есть");
                        js5.put("Количество слотов памяти", "4");
                        js5.put("Минимальная частота памяти", "2133 МГц");
                        js5.put("Максимальная частота памяти (МГц)", "2666 МГц");
                        js5.put("Количество каналов памяти", "2");
                        js5.put("Максимальный объем памяти", "64 ГБ");
                        jsonObject.put("Память", js5);
                        JSONObject js6 = new JSONObject();
                        js6.put("Тип и количество портов SATA", "6x SATA 6Gb/s");
                        js6.put("Количество портов SATA Express", "нет");
                        js6.put("Количество разъемов M.2", "3");
                        js6.put("Форм-фактор M.2 накопителя", "2280, 2230, 22110, 2260, 2242");
                        js6.put("Поддержка NVMe", "есть");
                        js6.put("Режим работы SATA RAID", "1, 0, 10, 5");
                        js6.put("Разъем mSATA", "нет");
                        js6.put("Контроллер IDE", "нет");
                        jsonObject.put("Контроллеры накопителей", js6);
                        JSONObject js7 = new JSONObject();
                        js7.put("Количество слотов PCI-E x16", "2");
                        js7.put("Количество слотов PCI-E x8", "нет");
                        js7.put("Количество слотов PCI-E x4", "нет");
                        js7.put("Количество слотов PCI-E x1", "1");
                        js7.put("Режимы работы нескольких PCI-E x16 слотов", "16-4, 16-0");
                        js7.put("Версия PCI Express", "3.0");
                        js7.put("Количество слотов PC", "нет");
                        jsonObject.put("Слоты расширения", js7);
                        JSONObject js8 = new JSONObject();
                        js8.put("Внутренние коннекторы USB на плате", "4x USB 2.0, 2x USB 3.0");
                        js8.put("Количество и тип USB на задней панели", "1x USB 3.0 Type-C, 2x USB 2.0, 4x USB 3.0");
                        js8.put("Видео выходы", "1x DisplayPort, 1x HDMI");
                        js8.put("Количество аналоговых аудио разъёмов", "5");
                        js8.put("Цифровые аудио порты (S/PDIF)", "оптический выход");
                        js8.put("Порты PS/2", "комбинированный");
                        js8.put("Другие разъёмы на задней панели", "нет");
                        js8.put("Количество сетевых портов (RJ-45)", "1");
                        jsonObject.put("Задняя панель", js8);
                        JSONObject js9 = new JSONObject();
                        js9.put("Звук", "Realtek HD Audio");
                        js9.put("Звуковая схема", "7.1");
                        js9.put("Чипсет звукового адаптера", "Realtek ALC1200");
                        jsonObject.put("Аудио", js9);
                        JSONObject js10 = new JSONObject();
                        js10.put("Чипсет сетевого адаптера", "Intel I219V");
                        js10.put("Скорость сетевого адаптера", "1000 Мбит/с");
                        js10.put("Встроенный адаптер Wi-Fi", "нет");
                        js10.put("Bluetooth", "нет");
                        jsonObject.put("Сеть", js10);

                        JSONObject js11 = new JSONObject();
                        js11.put("Разъем питания процессорного кулера", "4-pin x 2");
                        js11.put("4-pin разъемы для системных вентиляторов", "3");
                        js11.put("3-pin разъемы для системных вентиляторов", "нет");
                        jsonObject.put("Охлаждение", js11);


                        JSONObject js12 = new JSONObject();
                        js12.put("Основной разъем питания", "24-pin");
                        js12.put("Разъем питания процессора", "8-pin");
                        js12.put("Количество фаз питания", "8");
                        jsonObject.put("Питание", js12);

                        JSONObject js13 = new JSONObject();
                        js13.put("Подсветка элементов платы", "есть");
                        js13.put("Интерфейс LPT", "нет");
                        js13.put("Комплектация", "3 x винт для разъема M.2, заглушка для задней панели ввода/вывода, диск с ПО, документация, кабель SATA x2");
                        js13.put("Особенности, дополнительно", "поддержка процессоров с TDP до 95 Вт");
                        jsonObject.put("Дополнительные параметры", js13);

                        Product p1 = new Product(10, "Материнская плата ASRock B365M PHANTOM GAMING 4", "Приготовьтесь к путешествию в безграничный мир виртуальной реальности с ASRock B365M Phantom Gaming 4! А перед этим убедитесь, что у вас установлены все необходимые устройства, программы и драйверы. Все компоненты вашего ПК подключаются к материнской плате, а с комплектом ASRock VR Ready вы получите именно то, о чём мечтаете! Высочайшее качество изделия подтверждено всесторонними проверками на стадии разработки. Надёжные компоненты и молниеносная скорость работы обеспечат глубокое погружение в мир ВР. Мы гарантируем, что с нашими материнскими платами у вас не будет никаких технических проблем с виртуальной реальностью!",
                                6999, 3, jsonObject.toString(), new DateTime());
                        productService.addProduct(p1);
                        break;
                    case 6:
                        System.exit(0);
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public static void menu(){
        System.out.println("NNS-SHOP - Java, MAVEN, MySQL(ORM - HIBERNATE)");
        System.out.println("Вариант 17 - Николаев Н.С.");
        System.out.println("Меню команд на сервере:");
        System.out.println("1. Запустить сервер");
        System.out.println("2. Выполнить выборку");
        System.out.println("3. Выполнить обновление");
        System.out.println("4. Обновить характеристики");
        System.out.println("5. Добавить товары");
        System.out.println("6. Выключить программу");
    }
}

