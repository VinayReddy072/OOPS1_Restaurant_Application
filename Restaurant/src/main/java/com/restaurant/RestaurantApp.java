package com.restaurant;

import com.restaurant.model.*;
import com.restaurant.service.*;
import com.restaurant.exception.*;
import com.restaurant.util.MenuFilters;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.Headers;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonPrimitive;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class RestaurantApp {

    // JSON Parser with LocalDateTime support
    private static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class,
                    (JsonSerializer<LocalDateTime>) (src, type, ctx) -> new JsonPrimitive(src.toString()))
            .registerTypeAdapter(LocalDateTime.class,
                    (JsonDeserializer<LocalDateTime>) (json, type, ctx) -> LocalDateTime.parse(json.getAsString()))
            .setPrettyPrinting()
            .create();

    // Ensure this folder exists next to your 'src' folder
    private static final Path FRONTEND_DIR = Paths.get("frontend");

    // DATA STORES
    private static final List<MenuItem> WEB_MENU = buildWebMenu();
    private static final Map<String, OrderRecord> ORDER_HISTORY = new ConcurrentHashMap<>();

    // Keeping a separate map for order status since Record is immutable
    private static final Map<String, OrderStatus> ORDER_STATUS_MAP = new ConcurrentHashMap<>();

    private enum OrderStatus {
        RECEIVED, COOKING, READY
    }

    // ========================= MAIN =========================

    public static void main(String[] args) {
        System.out.println("=== Restaurant Management System - Integrated Demo ===\n");

        // 1. Core OOP & Inheritance
        demonstrateInheritance();

        // 2. Interfaces (Billable & Discountable)
        demonstrateInterfacesAndBilling();

        // 3. Exceptions
        demonstrateExceptionHandling();

        // 4. Functional Programming (MenuFilters)
        demonstrateMenuFiltering();

        // 5. Records & Immutability
        demonstrateRecordsAndImmutability();

        // 6. Sealed Classes (Switch Pattern Matching)
        demonstrateSealedDiscounts();

        // ----------------- NEW: Missing OOP & Advanced Java Concepts -----------------
        demonstrateThisVsThisConstructor();
        demonstrateOverloading();
        demonstrateVarargs();
        demonstrateLVTI();
        demonstrateEncapsulation();
        demonstrateOverridingPolymorphism();
        demonstrateSuperVsSuperMethod();
        demonstrateCheckedUnchecked();
        demonstrateArrays();
        demonstrateCoreAPI();
        demonstrateCallByValue();
        demonstrateDefensiveCopying();
        demonstrateInterfaceFeatures();
        demonstrateLambdas();
        demonstrateMethodReferences();
        demonstrateFinalAndEffectivelyFinal();
        demonstrateSwitchExpressions();
        // ---------------------------------------------------------------------------

        System.out.println("\n=== Core Demo Complete ===");

        try {
            startHttpServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ========================= DEMO METHODS =========================

    private static void demonstrateInheritance() {
        System.out.println("\n--- 1. Inheritance (Person vs Employee) ---");
        try {
            Employee emp = new Employee("Chef Ramsey", 45, "ramsey@kitchen.com", "EMP-999", 85000);
            emp.displayInfo(); // Overridden method
        } catch (Exception e) {
            System.out.println("Error creating employee: " + e.getMessage());
        }
    }

    private static void demonstrateInterfacesAndBilling() {
        System.out.println("\n--- 2. Interfaces (Billable & Discountable) ---");
        Order order = new Order("DEMO-001");
        order.addItems(
                new MenuItem("Steak", 30.00, MealType.DINNER),
                new MenuItem("Wine", 15.00, MealType.DINNER));

        System.out.println("Subtotal: " + Billable.formatCurrency(order.calculateTotal()));
        System.out.println("With Tax: " + Billable.formatCurrency(order.calculateTotalWithTax()));
        System.out.println("Grand Total (Tax + 20% Tip): " + Billable.formatCurrency(order.calculateGrandTotal(20)));
    }

    private static void demonstrateExceptionHandling() {
        System.out.println("\n--- 3. Custom Exceptions ---");
        try {
            createRiskyItem("  ", -10.0);
        } catch (InvalidMenuItemException e) {
            System.out.println("Caught Expected Checked Exception: " + e.getMessage());
        }

        try {
            throw new OrderNotFoundException("ORD-GHOST-999");
        } catch (OrderNotFoundException e) {
            System.out.println("Caught Expected Runtime Exception: " + e.getMessage());
        }
    }

    private static void createRiskyItem(String name, double price) throws InvalidMenuItemException {
        if (name == null || name.isBlank())
            throw new InvalidMenuItemException("Name empty");
        if (price < 0)
            throw new InvalidMenuItemException("Price negative");
    }

    private static void demonstrateMenuFiltering() {
        System.out.println("\n--- 4. MenuFilters & Predicates ---");
        List<MenuItem> items = WEB_MENU;

        System.out.println("Items cheaper than $10:");
        List<MenuItem> cheapEats = MenuFilters.filterItems(items, MenuFilters.priceLessThan(10.0));
        MenuFilters.printItems(cheapEats);

        System.out.println("\nTotal Price of Menu: " + Billable.formatCurrency(MenuFilters.getTotalPrice(items)));
    }

    private static void demonstrateRecordsAndImmutability() {
        System.out.println("\n--- 5. Records & ImmutableMenu ---");
        ImmutableMenu safeMenu = new ImmutableMenu("Safe Menu", WEB_MENU);
        System.out.println(
                "Immutable Menu Created: " + safeMenu.getMenuName() + " with " + safeMenu.getItemCount() + " items.");

        OrderRecord record = new OrderRecord("REC-1", "Alice", safeMenu.getItems(), LocalDateTime.now());
        System.out.println("OrderRecord stored for: " + record.customerName());
    }

    private static void demonstrateSealedDiscounts() {
        System.out.println("\n--- 6. Sealed Interfaces & Pattern Matching ---");
        Discountable d = new VIPDiscount();

        String type = switch (d) {
            case VIPDiscount v -> "VIP (20%)";
            case StudentDiscount s -> "Student (10%)";
            case SeniorDiscount s -> "Senior (15%)";
        };
        System.out.println("Identified Discount Type: " + type);
        System.out.println("Discount on $100: " + d.applyDiscount(100.0));
    }

    // ========================= NEW DEMOS: Missing OOP & Advanced Java
    // =========================

    // 1. this() vs this.
    private static void demonstrateThisVsThisConstructor() {
        System.out.println("\n--- this() vs this. ---");

        class Demo {
            int x;

            Demo() {
                this(10); // calls other constructor (this())
            }

            Demo(int x) {
                this.x = x; // this. refers to instance field
            }
        }

        Demo d = new Demo();
        System.out.println("Value via this(): " + d.x);
    }

    // 2. Method Overloading
    private static void demonstrateOverloading() {
        System.out.println("\n--- Method Overloading ---");

        class Calc {
            int add(int a, int b) {
                return a + b;
            }

            double add(double a, double b) {
                return a + b;
            }

            int add(int a, int b, int c) {
                return a + b + c;
            }
        }

        Calc c = new Calc();
        System.out.println("add(2,3) = " + c.add(2, 3));
        System.out.println("add(2.5,4.2) = " + c.add(2.5, 4.2));
        System.out.println("add(1,2,3) = " + c.add(1, 2, 3));
    }

    // 3. Varargs
    private static void demonstrateVarargs() {
        System.out.println("\n--- Varargs ---");

        class Printer {
            void printAll(String... msgs) {
                for (String m : msgs)
                    System.out.println(m);
            }
        }

        new Printer().printAll("Hello", "Varargs", "World");
    }

    // 4. LVTI (var)
    private static void demonstrateLVTI() {
        System.out.println("\n--- LVTI (var) ---");
        var list = new ArrayList<String>();
        list.add("Java");
        list.add("LVTI");
        System.out.println(list);
    }

    // 5. Encapsulation
    private static void demonstrateEncapsulation() {
        System.out.println("\n--- Encapsulation ---");

        Person p = new Person("Alice", 25);
        p.setEmail("alice@mail.com"); // controlled access via setter
        System.out.println("Name: " + p.getName());
        System.out.println("Email: " + p.getEmail());
    }

    // 6. Overriding & Polymorphism
    private static void demonstrateOverridingPolymorphism() {
        System.out.println("\n--- Overriding & Polymorphism ---");

        Person p = new Employee("Bob", 30, "bob@mail.com", "E101", 50000);
        p.displayInfo(); // runtime polymorphism: Employee.displayInfo()
    }

    // 7. super() vs super.method
    private static void demonstrateSuperVsSuperMethod() {
        System.out.println("\n--- super() vs super.method ---");

        class Base {
            Base() {
                System.out.println("Base constructor");
            }

            void show() {
                System.out.println("Base.show()");
            }
        }

        class Child extends Base {
            Child() {
                super();
            } // call parent constructor

            @Override
            void show() {
                super.show(); // call parent method
                System.out.println("Child.show()");
            }
        }

        new Child().show();
    }

    // 8. Checked vs Unchecked Exceptions
    private static void demonstrateCheckedUnchecked() {
        System.out.println("\n--- Checked vs Unchecked Exceptions ---");

        try {
            throwChecked();
        } catch (Exception e) {
            System.out.println("Checked Exception caught: " + e.getMessage());
        }

        try {
            throwUnchecked();
        } catch (RuntimeException e) {
            System.out.println("Unchecked Exception caught: " + e.getMessage());
        }
    }

    private static void throwChecked() throws Exception {
        throw new Exception("This is a checked Exception");
    }

    private static void throwUnchecked() {
        throw new RuntimeException("This is an unchecked RuntimeException");
    }

    // 9. Arrays
    private static void demonstrateArrays() {
        System.out.println("\n--- Arrays ---");
        MenuItem[] arr = WEB_MENU.toArray(new MenuItem[0]);
        if (arr.length > 0) {
            System.out.println("First item (array): " + arr[0]);
        } else {
            System.out.println("Menu empty (array demo)");
        }
    }

    // 10. Core Java API: StringBuilder, List/ArrayList, Date API
    private static void demonstrateCoreAPI() {
        System.out.println("\n--- Core Java API ---");
        StringBuilder sb = new StringBuilder();
        sb.append("Time now: ").append(LocalDateTime.now());
        System.out.println(sb.toString());

        List<String> names = new ArrayList<>();
        names.add("Alice");
        names.add("Bob");
        System.out.println("Names: " + names);
    }

    // 11. Call-by-value
    private static void demonstrateCallByValue() {
        System.out.println("\n--- Call-by-Value ---");
        int x = 10;
        modifyPrimitive(x);
        System.out.println("After modifyPrimitive(x): x = " + x); // unchanged

        MenuItem original = WEB_MENU.get(0);
        tryModifyMenuItem(original);
        System.out.println("MenuItem after tryModifyMenuItem (immutable fields): " + original);
    }

    private static void modifyPrimitive(int x) {
        x = 99; // local change only
    }

    private static void tryModifyMenuItem(MenuItem m) {
        // Can't change final fields of MenuItem; can only reassign local ref
        m = new MenuItem("Hacked", 0.0, MealType.BREAKFAST);
    }

    // 12. Defensive Copying (call-by-value + immutability)
    private static void demonstrateDefensiveCopying() {
        System.out.println("\n--- Defensive Copying ---");
        List<MenuItem> copy = List.copyOf(WEB_MENU); // unmodifiable defensive copy
        System.out.println("Made defensive copy of WEB_MENU (immutable view). Size: " + copy.size());
        try {
            copy.add(null);
        } catch (UnsupportedOperationException e) {
            System.out.println("Immutable defensive copy prevents modification: " + e.getClass().getSimpleName());
        }
    }

    // 13. Private/default/static methods in interfaces (Billable) demonstration
    private static void demonstrateInterfaceFeatures() {
        System.out.println("\n--- Interface Features (default/private/static) ---");
        Order o = new Order("I-1");
        o.addItems(WEB_MENU.get(0));
        System.out.println(
                "calculateTotalWithTax (default method): " + Billable.formatCurrency(o.calculateTotalWithTax()));
        System.out.println("static tax rate (Billable): " + Billable.getDefaultTaxRate());
    }

    // 14. Lambdas & Predicate (and final/effectively final discussion)
    private static void demonstrateLambdas() {
        System.out.println("\n--- Lambdas & Predicate ---");

        // lambda that checks cheap items
        List<MenuItem> cheap = MenuFilters.filterItems(WEB_MENU, item -> item.getPrice() < 10.0);
        System.out.println("Lambda filtered cheap items:");
        cheap.forEach(System.out::println);
    }

    // 15. Method References
    private static void demonstrateMethodReferences() {
        System.out.println("\n--- Method References ---");
        System.out.println("Printing WEB_MENU using method reference:");
        WEB_MENU.forEach(System.out::println); // System.out::println as method reference
    }

    // 16. final vs effectively final
    private static void demonstrateFinalAndEffectivelyFinal() {
        System.out.println("\n--- final vs effectively final ---");
        final int a = 10; // explicitly final
        int b = 20; // effectively final (not reassigned)
        Runnable r = () -> System.out.println("a + b = " + (a + b));
        r.run();
    }

    // 17. Switch expressions (enhanced switch)
    private static void demonstrateSwitchExpressions() {
        System.out.println("\n--- Switch Expressions ---");
        MealType type = MealType.DINNER;
        String msg = switch (type) {
            case BREAKFAST -> "Morning meals";
            case LUNCH -> "Midday meals";
            case DINNER -> "Evening meals";
            case LATE_NIGHT -> "Night cravings";
        };
        System.out.println("Switch expression result: " + msg);
    }

    // ========================= WEB BACKEND =========================

    private static void startHttpServer() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        // --- Static Files ---
        server.createContext("/", exchange -> {
            addCors(exchange);
            if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
                sendNoContent(exchange);
                return;
            }
            String path = exchange.getRequestURI().getPath();
            if (path == null || "/".equals(path))
                path = "/index.html";
            Path file = FRONTEND_DIR.resolve(path.substring(1)).normalize();
            if (!Files.exists(file)) {
                sendTextResponse(exchange, 404, "Not Found");
                return;
            }
            byte[] bytes = Files.readAllBytes(file);
            exchange.getResponseHeaders().add("Content-Type", guessContentType(file.getFileName().toString()));
            exchange.sendResponseHeaders(200, bytes.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(bytes);
            }
        });

        // --- GET /menu (With Filtering) ---
        server.createContext("/menu", exchange -> {
            addCors(exchange);
            if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
                sendNoContent(exchange);
                return;
            }
            if (!"GET".equalsIgnoreCase(exchange.getRequestMethod())) {
                exchange.sendResponseHeaders(405, -1);
                return;
            }

            Map<String, String> params = parseQuery(exchange.getRequestURI().getQuery());
            List<MenuItem> result = new ArrayList<>(WEB_MENU);

            if (params.containsKey("maxPrice")) {
                try {
                    double max = Double.parseDouble(params.get("maxPrice"));
                    result = MenuFilters.filterItems(result, MenuFilters.priceLessThan(max));
                } catch (NumberFormatException ignored) {
                }
            }
            if (params.containsKey("type")) {
                try {
                    MealType type = MealType.valueOf(params.get("type").toUpperCase());
                    result = MenuFilters.filterItems(result, MenuFilters.isMealType(type));
                } catch (IllegalArgumentException ignored) {
                }
            }

            sendJsonResponse(exchange, 200, result);
        });

        // --- POST /order (Uses Billable + Discountable) ---
        server.createContext("/order", exchange -> {
            addCors(exchange);
            if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
                sendNoContent(exchange);
                return;
            }
            if (!"POST".equalsIgnoreCase(exchange.getRequestMethod())) {
                exchange.sendResponseHeaders(405, -1);
                return;
            }

            String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
            Map<String, Object> reqData = parseJsonBody(body);

            String customer = (String) reqData.getOrDefault("customer", "Guest");
            String dishName = (String) reqData.getOrDefault("dish", "");
            String promoCode = (String) reqData.getOrDefault("promoCode", "");
            int qty = 1;
            try {
                qty = Integer.parseInt(String.valueOf(reqData.getOrDefault("qty", "1")));
            } catch (Exception e) {
            }

            Optional<MenuItem> itemOpt = WEB_MENU.stream()
                    .filter(m -> m.getName().equalsIgnoreCase(dishName)).findFirst();

            if (itemOpt.isEmpty()) {
                sendTextResponse(exchange, 400, "Dish not found: " + dishName);
                return;
            }

            String orderId = "ORD-" + System.currentTimeMillis();
            Order order = new Order(orderId);
            for (int i = 0; i < qty; i++) {
                order.addItem(itemOpt.get());
            }

            Discountable strategy = null;
            if ("VIP".equalsIgnoreCase(promoCode))
                strategy = new VIPDiscount();
            else if ("STUDENT".equalsIgnoreCase(promoCode))
                strategy = new StudentDiscount();
            else if ("SENIOR".equalsIgnoreCase(promoCode))
                strategy = new SeniorDiscount();

            double baseTotal = order.calculateTotal();
            double finalTotal = (strategy != null) ? strategy.applyDiscount(baseTotal) : baseTotal;

            OrderRecord record = new OrderRecord(orderId, customer, order.getItems(), LocalDateTime.now());
            ORDER_HISTORY.put(orderId, record);
            ORDER_STATUS_MAP.put(orderId, OrderStatus.RECEIVED);

            Map<String, Object> response = new HashMap<>();
            response.put("orderId", orderId);
            response.put("subtotal", baseTotal);
            response.put("discountApplied", (strategy != null) ? strategy.getDiscountDescription() : "None");
            response.put("finalTotal", Math.round(finalTotal * 100.0) / 100.0);

            sendJsonResponse(exchange, 200, response);
        });

        // --- GET /orders (Uses OrderRecord) ---
        server.createContext("/orders", exchange -> {
            addCors(exchange);
            if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
                sendNoContent(exchange);
                return;
            }

            List<OrderRecord> history = new ArrayList<>(ORDER_HISTORY.values());
            history.sort((a, b) -> b.orderTime().compareTo(a.orderTime()));

            sendJsonResponse(exchange, 200, history);
        });

        // --- GET /order-status ---
        server.createContext("/order-status", exchange -> {
            addCors(exchange);
            if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
                sendNoContent(exchange);
                return;
            }

            Map<String, String> params = parseQuery(exchange.getRequestURI().getQuery());
            String orderId = params.get("orderId");

            if (!ORDER_HISTORY.containsKey(orderId)) {
                sendTextResponse(exchange, 404, new OrderNotFoundException(orderId).getMessage());
                return;
            }

            OrderRecord record = ORDER_HISTORY.get(orderId);
            long seconds = Duration.between(record.orderTime(), LocalDateTime.now()).getSeconds();

            OrderStatus status = (seconds < 10) ? OrderStatus.RECEIVED
                    : (seconds < 30) ? OrderStatus.COOKING : OrderStatus.READY;

            Map<String, Object> resp = new HashMap<>();
            resp.put("orderId", orderId);
            resp.put("status", status);
            resp.put("secondsElapsed", seconds);
            sendJsonResponse(exchange, 200, resp);
        });

        server.setExecutor(null);
        System.out.println("HTTP server started on http://localhost:8080");
        server.start();
    }

    // ========================= HELPERS =========================

    private static List<MenuItem> buildWebMenu() {
        List<MenuItem> list = new ArrayList<>();
        list.add(new MenuItem("Breakfast Burrito", 9.99, MealType.BREAKFAST));
        list.add(new MenuItem("Pancakes", 8.49, MealType.BREAKFAST));
        list.add(new MenuItem("Avocado Toast", 7.99, MealType.BREAKFAST)); // New item
        list.add(new MenuItem("Eggs Benedict", 12.49, MealType.BREAKFAST)); // New item

        list.add(new MenuItem("Caesar Salad", 11.99, MealType.LUNCH));
        list.add(new MenuItem("Grilled Chicken Salad", 13.49, MealType.LUNCH)); // New item
        list.add(new MenuItem("Veggie Burger", 12.99, MealType.LUNCH)); // New item
        list.add(new MenuItem("Burger", 13.99, MealType.LUNCH));
        list.add(new MenuItem("Chicken Sandwich", 14.49, MealType.LUNCH)); // New item

        list.add(new MenuItem("Steak", 29.99, MealType.DINNER));
        list.add(new MenuItem("Pasta", 18.99, MealType.DINNER));
        list.add(new MenuItem("Salmon Fillet", 24.99, MealType.DINNER)); // New item
        list.add(new MenuItem("Ribeye Steak", 32.99, MealType.DINNER)); // New item
        list.add(new MenuItem("Grilled Lobster", 39.99, MealType.DINNER)); // New item

        list.add(new MenuItem("Ice Cream", 5.99, MealType.LATE_NIGHT));
        list.add(new MenuItem("Chocolate Cake", 6.99, MealType.LATE_NIGHT)); // New item
        list.add(new MenuItem("Cheesecake", 7.49, MealType.LATE_NIGHT)); // New item
        list.add(new MenuItem("Milkshake", 4.99, MealType.LATE_NIGHT)); // New item
        list.add(new MenuItem("Brownie Sundae", 5.49, MealType.LATE_NIGHT)); // New item

        return list;
    }

    private static void sendJsonResponse(HttpExchange exchange, int status, Object obj) throws IOException {
        String json = GSON.toJson(obj);
        byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(status, bytes.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(bytes);
        }
    }

    private static void sendTextResponse(HttpExchange exchange, int status, String text) throws IOException {
        byte[] bytes = text.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", "text/plain");
        exchange.sendResponseHeaders(status, bytes.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(bytes);
        }
    }

    private static void sendNoContent(HttpExchange exchange) throws IOException {
        Headers rh = exchange.getResponseHeaders();
        rh.add("Access-Control-Allow-Methods", "GET,POST,OPTIONS");
        rh.add("Access-Control-Allow-Headers", "Content-Type");
        exchange.sendResponseHeaders(204, -1);
    }

    private static void addCors(HttpExchange exchange) {
        Headers h = exchange.getResponseHeaders();
        h.add("Access-Control-Allow-Origin", "*");
        h.add("Access-Control-Allow-Credentials", "true");
    }

    private static Map<String, String> parseQuery(String query) {
        Map<String, String> map = new HashMap<>();
        if (query == null || query.isBlank())
            return map;
        for (String pair : query.split("&")) {
            String[] kv = pair.split("=", 2);
            if (kv.length == 2)
                map.put(kv[0], kv[1]);
        }
        return map;
    }

    private static Map<String, Object> parseJsonBody(String body) {
        try {
            return GSON.fromJson(body, new com.google.gson.reflect.TypeToken<Map<String, Object>>() {
            }.getType());
        } catch (Exception e) {
            return new HashMap<>();
        }
    }

    private static String guessContentType(String name) {
        if (name.endsWith(".html"))
            return "text/html";
        if (name.endsWith(".js"))
            return "application/javascript";
        if (name.endsWith(".css"))
            return "text/css";
        return "application/octet-stream";
    }
}