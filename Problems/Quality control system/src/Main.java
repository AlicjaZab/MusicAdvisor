import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.WildcardType;
import java.util.List;

class QualityControl {

    public static boolean check(List<Box<? extends Bakery>> boxes){
        try {
            //check if list is empty
            if (boxes.size() == 0)
                return true;

            for (Object b : boxes) {

                //check if b is a box
                String c = b.getClass().getName();
                if (!c.equals("Box"))
                    return false;

                //check if b is empty
                if (((Box) b).get() == null)
                    return false;

                //check if b contains object of bakery or its subclasses
                Class clazz = ((Box) b).get().getClass();
                String superclass = clazz.getSuperclass().getName();
                if (!superclass.equals("Bakery") && !clazz.getName().equals("Bakery"))
                    return false;

            }

            return true;
        }catch(Exception e){
            return false;
        }

    }

}

// Don't change the code below
class Bakery { }

class Cake extends Bakery { }

class Pie extends Bakery { }

class Tart extends Bakery { }

class Paper { }

class Box<T> {

    private T item;

    public void put(T item) {
        this.item = item;
    }

    public T get() {
        return this.item;
    }
}