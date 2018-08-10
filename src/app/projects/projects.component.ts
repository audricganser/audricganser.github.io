import { Component, OnInit } from '@angular/core';
import { Project } from '../project';

@Component({
  selector: 'app-projects',
  templateUrl: './projects.component.html',
  styleUrls: ['./projects.component.css']
})
export class ProjectsComponent implements OnInit {

  projects: Project[] = [
    {
      name: "Project 2021",
      descript: "null",
      img: "thumb-Project2021.png",
      link: "http://test.project2021.utexas.edu/development-studio/"
    },
    {
      name: "La Belle TBH",
      descript: "null",
      img: "thumb-LaBelleTBH.png",
      link:"http://www.texasbeyondhistory.net/belle/explore.html"
    },
    {
      name: "What Jane Saw Catalogue",
      descript: "null",
      img: "thumb-WhatJaneSaw.png",
      link:"http://www.nytimes.com/2015/12/17/arts/design/for-a-shakespeare-anniversary-an-online-re-creation-of-a-1796-show.html"
    },
    {
      name: "Tertis Pavlov",
      descript: "null",
      img: "thumb-TertisPavlov.png",
      link:"http://sites.utexas.edu/tertispavlovproject/"
    },
    {
      name: "Lifespan Dev Lab",
      descript: "null",
      img: "thumb-LifespanDevLab.png",
      link:"http://labs.la.utexas.edu/tucker-drob/"
    },
    {
      name:  "Child Dev In Context Lab",
      descript: "null",
      img: "thumb-ChildDevInContextLab.png",
      link:"http://labs.la.utexas.edu/neal/"
    }
  ];

  constructor() { }

  ngOnInit() {
  }

}
