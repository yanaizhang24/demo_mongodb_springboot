package cn.yanf.webmagic.piplline;

import cn.yanf.Repository.TieBarRepository;
import cn.yanf.entity.AlibabaEN;
import cn.yanf.entity.TieBar;
import cn.yanf.entity.TieBarBiaoQ;
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
    public MongodbPipeline( TieBarRepository repository){
        this.repository=repository;
    }
    public void process(ResultItems resultItems, Task task) {
        System.out.println("get page: " + resultItems.getRequest().getUrl());
        Iterator i$ = resultItems.getAll().entrySet().iterator();
        while(i$.hasNext()) {
            Map.Entry entry = (Map.Entry)i$.next();
            System.out.println((String)entry.getKey() + ":\t" + entry.getValue());
        }
        if(resultItems.getAll().containsKey("list")){
            for(TieBar tb:(List<TieBar>)resultItems.getAll().get("list")){
                repository.save(tb);
            }
        }
        if(resultItems.getAll().containsKey("list2")){
            for(TieBar tb:(List<TieBar>)resultItems.getAll().get("list2")){
                repository.save(tb);
            }
        }
        if(resultItems.getAll().containsKey("list_ali")){
            for(TieBar ali:(List<TieBar>)resultItems.getAll().get("list_ali")){
                repository.save(ali);
            }
        }



    }
}
