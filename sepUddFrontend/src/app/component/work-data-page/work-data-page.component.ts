import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';
import {ToastrManager} from 'ng6-toastr-notifications';
import {WorkDataDTO} from '../model/WorkDataDTO';
import {WorkService} from '../../service/work.service';

@Component({
  selector: 'app-work-data-page',
  templateUrl: './work-data-page.component.html',
  styleUrls: ['./work-data-page.component.css']
})
export class WorkDataPageComponent implements OnInit {

  private href: any;
  private fileField = null;
  private fileName = null;
  workData: WorkDataDTO = new WorkDataDTO();

  constructor(private router: Router, private toastr: ToastrManager, private workService: WorkService) {
    this.href = this.router.url;

  }

  ngOnInit() {

  }


  submitWorkData() {

    this.workData.file = this.fileField.toString();
    this.workData.fileName = this.fileName.toString();
    console.log(this.workData.fileName + ' FILE NAME !');

    this.workService.submitWorkData(this.workData).subscribe( res => {
          console.log(res);
          this.toastr.successToastr('Success!');


        }, err => {
          this.toastr.errorToastr("Greska");
        }
    );
  }

  fileChooserListener(files: FileList, field) {
    this.fileName = files.item(0).name;

    const fileReader = new FileReader();

    fileReader.onload = (e) => {

      this.fileField = fileReader.result;
    };

    fileReader.readAsDataURL(files.item(0));
  }

}
