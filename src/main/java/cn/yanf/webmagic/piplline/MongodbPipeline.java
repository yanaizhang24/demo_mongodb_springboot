package cn.yanf.webmagic.piplline;

import cn.yanf.Repository.CustomerRepository;
import cn.yanf.Repository.TieBarRepository;
import cn.yanf.entity.TieBar;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/18 0018.
 */
public class MongodbPipeline implements Pipeline {
    public MongodbPipeline() {
    }
    private List<TieBar> t;
    private TieBarRepository repository;
    public MongodbPipeline(List<TieBar> t, TieBarRepository repository){
        this.t=t;
        this.repository=repository;
    }
    public void process(ResultItems resultItems, Task task) {
        System.out.println("get page: " + resultItems.getRequest().getUrl());
        Iterator i$ = resultItems.getAll().entrySet().iterator();

        while(i$.hasNext()) {
            Map.Entry entry = (Map.Entry)i$.next();
            System.out.println((String)entry.getKey() + ":\t" + entry.getValue());
        }
        for(TieBar t_t:t){
            repository.save(t_t);
        }

    }
}
