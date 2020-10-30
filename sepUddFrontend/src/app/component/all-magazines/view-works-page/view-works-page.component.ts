import {Component, Inject, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {WorkService} from "../../../service/work.service";
import {DOCUMENT} from "@angular/common";
import {ToastrManager} from "ng6-toastr-notifications";

@Component({
  selector: 'app-view-works-page',
  templateUrl: './view-works-page.component.html',
  styleUrls: ['./view-works-page.component.css']
})
export class ViewWorksPageComponent implements OnInit {

  works: any;
  
  constructor(private route: ActivatedRoute, private workService: WorkService, private router: Router,
              @Inject(DOCUMENT) private document: Document, public toastr: ToastrManager) { }

  ngOnInit() {
    const magazineId = this.route.snapshot.paramMap.get('id');
    this.workService.getWorksByMagazineId(magazineId).subscribe(res => {
      this.works = res;
      console.log(res);


    }, error => {
      console.log('greska getworksbymagazineid');
    });


  }

  downloadWork(id: any) {
    console.log('preuzmi rad id: ' + id);

    this.workService.downloadWork(id).subscribe(
      res => {
        var blob = new Blob([res], {type: 'application/pdf'});
        var url = window.URL.createObjectURL(blob);
        window.open(url, "_blank");
      }, err => {
        alert("Error while download file");
      }
    );
  }

  buyWork(id: any) {

    this.workService.buyWork(id).subscribe(res => {

      console.log(res);
      this.document.location.href = res.url;

      this.workService.complete(res.url).subscribe(ret => {
        console.log('success');
      }, err => {
        console.log('complete error ');
      });

    }, error => {

      this.workService.errorComplete(error).subscribe( val => {
        console.log('error buying');
      });
      this.toastr.errorToastr('An error happened, please try again.')
      console.log('greska buymagazine');
    });
  }



}
