/**
 * 
 */
package ShredderUI;

/**
 * @author Selami
 *
 */
public interface ShredObservable {
public void notifyServer(long val);
public void add(ShredObserver o);
public void remove(ShredObserver o);

}
