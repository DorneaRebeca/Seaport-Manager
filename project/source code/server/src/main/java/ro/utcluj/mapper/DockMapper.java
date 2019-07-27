package ro.utcluj.mapper;

import org.springframework.stereotype.Component;
import ro.utcluj.api.DockBaseDTO;
import ro.utcluj.model.Dock;

import java.util.LinkedList;
import java.util.List;

@Component
public class DockMapper {


    public DockBaseDTO map(Dock dock){
        DockBaseDTO d = new DockBaseDTO ();
        d.setIddocks (dock.getIddocks ());
        d.setPrice (dock.getPrice ());
        d.setRegion (dock.getRegion ());
        d.setSize (dock.getSize ());
        return d;

    }


    public List<DockBaseDTO> mapAll(List<Dock> docks){
        List<DockBaseDTO> cls = new LinkedList<> ();
        for(Dock c : docks)
            cls.add (map (c));
        return cls;
    }

    public List<Dock> mapAllBack(List<DockBaseDTO> docks){
        List<Dock> cls = new LinkedList<> ();
        for(DockBaseDTO dock : docks){
            Dock d = new Dock ();
            d.setIddocks (dock.getIddocks ());
            d.setPrice (dock.getPrice ());
            d.setRegion (dock.getRegion ());
            d.setSize (dock.getSize ());
            cls.add (d);

        }
        return cls;
    }


}
