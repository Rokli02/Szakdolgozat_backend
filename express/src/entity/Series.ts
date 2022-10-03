import { Column, CreateDateColumn, Entity, JoinColumn, JoinTable, ManyToMany, OneToMany, PrimaryGeneratedColumn } from 'typeorm';
import { Category } from './Category';
import { NewsFeed } from './NewsFeed';
import { Season } from './Season';

@Entity()
export class Series {
  @PrimaryGeneratedColumn()
  id: number;

  @Column({ nullable: false })
  title: string;

  @Column({
    type: 'year',
    nullable: false
  })
  prodYear: number;

  @Column({nullable: false})
  ageLimit: number;

  @Column({nullable: false})
  length: number; // in minutes

  @CreateDateColumn()
  added: string;

  @OneToMany(() => NewsFeed, feed => feed.series)
  newsfeeds: NewsFeed[];

  @OneToMany(() => Season, season => season.series, {
    eager: true,
    cascade: ['insert', 'update']
  })
  seasons: Season[]

  @ManyToMany(() => Category, category => category.serieses, {
    eager: true,
    onUpdate: 'CASCADE'
    //cascade: true //Talán kell ez ide, de nem biztos
  })
  @JoinTable()
  categories: Category[]
}