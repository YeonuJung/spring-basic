package hello.core.singleton;

public class SingleTonService {

    // static으로 선언해서 메서드 영역에 하나만 생길 수 있도록
    // static이 아니면 getter로 가져올 때 항상 새로운 인스턴스가 생성된다
    private static final SingleTonService instance = new SingleTonService();

    // 인스턴스 생성 못하도록 막음
    private SingleTonService(){

    }
    // static 없이는 호출불가(인스턴스 생성 불가하기 때문에)
    public static SingleTonService getInstance() {
        return instance;
    }

    public void logic(){
        System.out.println("싱글톤 객체 로직 호출");
    }
}
