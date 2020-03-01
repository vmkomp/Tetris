public class Piste{
    private int x;
    private int y;

    public int annaX() {
        return x;
    }

    public void asetaX(int x) {
        this.x = x;
    }

    public int annaY() {
        return y;
    }

    public void asetaY(int y) {
        this.y = y;
    }

    public Piste(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Piste() {
    }
}