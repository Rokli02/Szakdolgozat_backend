import { Column, Entity, ManyToOne, PrimaryGeneratedColumn, UpdateDateColumn } from 'typeorm';
import { Series } from './Series';

@Entity()
export class NewsFeed {
  @PrimaryGeneratedColumn()
  id: number;

  @Column({ nullable: false })
  title: string;

  @Column({ type: 'text' })
  description: string;

  @UpdateDateColumn()
  modification: string;

  @ManyToOne(() => Series, series => series.newsfeeds, {
    nullable: false,
    onDelete: 'CASCADE'
  })
  series: Series;
}