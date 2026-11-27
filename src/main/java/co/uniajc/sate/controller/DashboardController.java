package co.uniajc.sate.controller;

import co.uniajc.sate.service.DashboardService;
import co.uniajc.sate.view.DashboardResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(
            DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping
    public DashboardResponse resumen() {
        return dashboardService.resumen();
    }
}
