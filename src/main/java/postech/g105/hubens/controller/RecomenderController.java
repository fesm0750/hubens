package postech.g105.hubens.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import postech.g105.hubens.controller.response.VideoResponse;
import postech.g105.hubens.service.RecommenderService;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/recommender/{idUsuario}")
public class RecomenderController {

    private final RecommenderService recommenderService;

    public RecomenderController(RecommenderService recommenderService) {
        this.recommenderService = recommenderService;
    }

    @GetMapping
    public Flux<VideoResponse> recomendar(@PathVariable String idUsuario) {
        return recommenderService.recomendar(idUsuario).map(video -> new VideoResponse(video));
    }
    
}
