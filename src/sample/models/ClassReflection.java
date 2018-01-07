package sample.models;

import java.lang.reflect.*;

public class ClassReflection {
    private StringBuilder sb;

    public ClassReflection() {
        this.sb = new StringBuilder();
    }

    public String reflectClass(String reflectClass) {

        try {
            Class<?> klasa = Class.forName(reflectClass);
//            Object generickiObjekt = klasa.newInstance();

//            if (generickiObjekt instanceof YambPaper || generickiObjekt instanceof Player) {
//                YambPaper objekt = (YambPaper) generickiObjekt;

//                objekt.setDatum(LocalDate.now());
//                System.out.println("Današnji datum je : " + objekt.getDatum());

                // CLASS NAME

                sb.append("Class name: " + klasa.getSimpleName());
                sb.append(System.getProperty("line.separator"));
                sb.append("Full class name: " + klasa.getName());
                sb.append(System.getProperty("line.separator"));

                //CLASS MODIFIERS
                sb.append("Class modifier:");
                int klasModifiers = klasa.getModifiers();
                if (Modifier.isPublic(klasModifiers)) {
                    sb.append("public");
                }
                if (Modifier.isPrivate(klasModifiers)) {
                    sb.append("private");
                }
                if (Modifier.isAbstract(klasModifiers)) {
                    sb.append("abstract");
                }
                if (Modifier.isFinal(klasModifiers)) {
                    sb.append("final");
                }
                if (Modifier.isStatic(klasModifiers)) {
                    sb.append("static");
                }
                sb.append(System.getProperty("line.separator"));

                //CLASS INTERFACES
                Class[] listaSucelja = klasa.getInterfaces();
                System.out.println();
                sb.append(System.getProperty("line.separator"));
                sb.append("Interfaces");
                sb.append(System.getProperty("line.separator"));
                for (Class interfaces : listaSucelja) {
                    sb.append(interfaces.getName());
                    sb.append(System.getProperty("line.separator"));
                }
                sb.append(System.getProperty("line.separator"));


                //CONSTRUCTORS
                sb.append("Constructors:");
                sb.append(System.getProperty("line.separator"));
                Constructor[] constructors = klasa.getConstructors();
                for (Constructor c : constructors) {
                    int constructorMod = c.getModifiers();
                    if (Modifier.isPublic(constructorMod))
                        sb.append("public ");
                    if (Modifier.isPrivate(constructorMod))
                        sb.append("private ");
                    if (Modifier.isAbstract(constructorMod))
                        sb.append("abstract ");
                    if (Modifier.isFinal(constructorMod))
                        sb.append("final ");
                    if (Modifier.isStatic(constructorMod))
                        sb.append("static ");
                    String nazivMetode = klasa.getSimpleName();
                    sb.append(nazivMetode + "(");
                    Class[] tipArgumenata = c.getParameterTypes();
                    for (int brojac = 0; brojac < tipArgumenata.length; brojac++) {
                        String nazivParametra = tipArgumenata[brojac].getName();
                        sb.append(" " + nazivParametra);
                    }
                    sb.append(" );");
                    sb.append(System.getProperty("line.separator"));
                }


                //FIELDS
                sb.append(System.getProperty("line.separator"));
                sb.append("Fields");
                sb.append(System.getProperty("line.separator"));
                Field[] poljeVarijabli = klasa.getDeclaredFields();
                for (Field varijabla : poljeVarijabli) {
                    int fieldMod = varijabla.getModifiers();
                    if (Modifier.isPublic(fieldMod))
                        sb.append("public ");
                    if (Modifier.isPrivate(fieldMod))
                        sb.append("private ");
                    if (Modifier.isAbstract(fieldMod))
                        sb.append("abstract ");
                    if (Modifier.isFinal(fieldMod))
                        sb.append("final ");
                    if (Modifier.isStatic(fieldMod))
                        sb.append("static ");
                    sb.append(varijabla.getType().getSimpleName());
                    sb.append(" " + varijabla.getName() + ";");
                    sb.append(System.getProperty("line.separator"));
                }
                sb.append(System.getProperty("line.separator"));


                //METHODS
                sb.append("Methods: ");
                sb.append(System.getProperty("line.separator"));
                Method[] metode = klasa.getMethods();
                for (Method metoda : metode) {
                    int metodaMods = metoda.getModifiers();
                    if (Modifier.isPublic(metodaMods))
                        sb.append("public ");
                    if (Modifier.isPrivate(metodaMods))
                        sb.append("private ");
                    if (Modifier.isAbstract(metodaMods))
                        sb.append("abstract ");
                    if (Modifier.isFinal(metodaMods))
                        sb.append("final ");
                    if (Modifier.isStatic(metodaMods))
                        sb.append("static ");
                    sb.append(metoda.getReturnType() + " ");
                    sb.append(metoda.getName());
                    if (metoda.getParameters().length > 0) {
                        sb.append("(");
                        Parameter[] parameters = metoda.getParameters();
                        Class[] tipArgumenata = metoda.getParameterTypes();
                        for (int brojac = 0; brojac < tipArgumenata.length; brojac++) {
                            String nazivParametra = tipArgumenata[brojac].getSimpleName();
                            sb.append(nazivParametra);
                            sb.append(" " + parameters[brojac].getName());
                        }

                        sb.append(")");
                    } else {
                        sb.append("()");
                    }

                    sb.append(System.getProperty("line.separator"));

                }
                System.out.println(sb.toString());

//            } else {
//                System.out.println("Traženi objekt nije tipa 'TestingClass'");
//                System.exit(0);
//            }


        } catch (ClassNotFoundException  ex) {
            ex.printStackTrace();
        }
        String doc = sb.toString();
        return doc;
    }
}

