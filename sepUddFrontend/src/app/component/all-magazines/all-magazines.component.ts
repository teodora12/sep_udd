import {Component, Inject, OnInit} from '@angular/core';
import {MagazineService} from "../../service/magazine.service";
import {UserService} from "../../service/user.service";
import {Router} from "@angular/router";
import {DOCUMENT} from "@angular/common";
import {ToastrManager} from "ng6-toastr-notifications";

@Component({
  selector: 'app-all-magazines',
  templateUrl: './all-magazines.component.html',
  styleUrls: ['./all-magazines.component.css']
})
export class AllMagazinesComponent implements OnInit {

  magazines: any[];

  constructor(private magazineService: MagazineService, private userService: UserService, private router: Router,
              @Inject(DOCUMENT) private document: Document, public toastr: ToastrManager) { }

  ngOnInit() {

    this.magazineService.getAllMagazines().subscribe( res => {
      this.magazines = res;

      console.log(res);
    }, err => {
      console.log('greska getAllMagazines');
    });

  }

  buyMagazine(magazineId: any) {
    console.log(magazineId + ' id');
    console.log(this.userService.getToken() + ' token');
    this.magazineService.buyMagazine(magazineId).subscribe(res => {

      console.log(res);
      this.document.location.href = res.url;

      this.magazineService.complete(res.url).subscribe(ret => {
        console.log('success');
      }, err => {
        console.log('complete error ');
      });

    }, error => {

      this.magazineService.errorComplete(error).subscribe( val => {
        console.log('error buying');
      });
      this.toastr.errorToastr('An error happened, please try again.')
      console.log('greska buymagazine');
    });
  }

  viewWorks(magazineId: any) {
    this.router.navigate(['/magazine', magazineId]);
  }
}
