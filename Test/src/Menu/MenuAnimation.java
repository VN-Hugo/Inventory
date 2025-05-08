package Menu;

import com.formdev.flatlaf.util.Animator;
import java.util.HashMap;

// Class giúp mở rộng thu nhỏ menu
public class MenuAnimation {
    // Dùng Hash để tránh 2 animation chạy cùng lúc
    private static final HashMap<MenuItem, Animator> hash = new HashMap<>();
    
    
    // Thực hiện hiệu ứng
    public static void animate(MenuItem menu, boolean show) {
        if (hash.containsKey(menu) && hash.get(menu).isRunning()) {
            hash.get(menu).stop();
        }
        menu.setMenuShow(show);
        Animator animator = new Animator(400, new Animator.TimingTarget() {
            @Override
            public void timingEvent(float f) {
                if (show) {
                    menu.setAnimate(f);
                } else {
                    menu.setAnimate(1f - f);
                }
                menu.revalidate();
            }

            @Override
            public void end() {
                hash.remove(menu);
            }
        });
        animator.setResolution(1);
        animator.setInterpolator((float f) -> (float) (1 - Math.pow(1 - f, 3)));
        animator.start();
        hash.put(menu, animator);
    }
}